import { ChangeDetectorRef, Component, inject, OnInit, output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ImageService } from 'src/app/core/services/image.service';
import { PostService } from 'src/app/core/services/post.service';
import { TokenService } from 'src/app/core/services/token.service';
import { CreatePostDTO } from 'src/app/shared/dtos/create-post.dto';
import { ApiResponse } from 'src/app/shared/responses/api.response';

@Component({
  selector: 'app-new-post',
  templateUrl: './new-post.component.html',
  styleUrl: './new-post.component.css'
})
export class NewPostComponent implements OnInit {
  // ArrayBuffer la kieu du lieu dung de luu tru du lieu nhi phan
  selectedMediaList: { type: string; src: string | ArrayBuffer | null; file: File }[] = [];
  selectedLength = this.selectedMediaList.length;
  closeNewPost = output<void>();
  postService = inject(PostService);
  createPostForm!: FormGroup;
  tokenService = inject(TokenService);
  imageService = inject(ImageService);

  constructor(private cdRef: ChangeDetectorRef) {}
  get firstFourMedia() {
    return this.selectedMediaList.slice(0, 4);
  }

  triggerFileInput(): void {
    const fileInput = document.getElementById('fileInput') as HTMLInputElement;
    if (fileInput) {
      fileInput.click();
    }
  }
  onFilesSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
        const files = Array.from(input.files); // Chuyển FileList thành mảng
        this.selectedMediaList = []; // Khởi tạo danh sách tệp đã chọn

        files.forEach((file) => {
            const reader = new FileReader();
            reader.onload = (e) => {
                const fileType = file.type.startsWith('image') ? 'image' : file.type.startsWith('video') ? 'video' : null;
                if (fileType) {
                    this.selectedMediaList.push({
                        type: fileType,
                        src: e.target?.result || null, // Dữ liệu để hiển thị preview
                        file: file, // Đối tượng File gốc
                    });
                }
                this.cdRef.detectChanges(); // Buộc Angular kiểm tra lại và cập nhật DOM
            };
            reader.readAsDataURL(file);
        });
    }
}

  onCloseNewPostForm(): void {
    this.closeNewPost.emit();
  }

  ngOnInit(): void {
    this.createPostForm = new FormGroup({
      content: new FormControl('', [Validators.maxLength(1000)]),
      privacy: new FormControl('public', [Validators.required]),
    });
  }

  onSubmit(){
    if(this.createPostForm.valid){
      // Gửi dữ liệu lên server
      const formData = {
        author_id: this.tokenService.getProfileId(), 
        author_type: "profile",
        post_type: "normal",
        privacy: this.createPostForm.value.privacy,
        content: this.createPostForm.value.content,
        medias: this.selectedMediaList
        // medias: this.selectedMediaList.map((media) => {
        //   return {
        //     media_type: media.type,
        //     url: media.src as string,
        //   };
        // }),
      };
      const createPostDTO = new CreatePostDTO(formData);
      this.postService.createPost(createPostDTO).subscribe({
        next: (response: ApiResponse) => {
          const postId = response.data.id;
          const files = this.selectedMediaList.map((media) => media.file); // Lấy danh sách File gốc
          
          this.postService.uploadMedia(postId, files).subscribe({
            next: (response: ApiResponse) => {
              alert(response.message);
              this.onCloseNewPostForm();
            },
            error: (error) => {
              console.error('Lỗi khi tải tệp lên:', error);
            }
          });
        },
        error: (error) => {
          console.error('Lỗi khi tạo bài viết:', error);
        }
      });
    }
  }
  get fullname() : string{
    return this.tokenService.getFullNameProfile();
  }
  get avatar() : string{
    const avatarUrl = this.tokenService.getAvatar();
    return this.imageService.getAvatar(avatarUrl);
  }
}
