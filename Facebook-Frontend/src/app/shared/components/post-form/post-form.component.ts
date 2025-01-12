import { ChangeDetectorRef, Component, inject, input, OnInit, output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ImageService } from 'src/app/core/services/image.service';
import { PostService } from 'src/app/core/services/post.service';
import { TokenService } from 'src/app/core/services/token.service';
import { CreatePostDTO } from '../../dtos/create-post.dto';
import { ApiResponse } from '../../responses/api.response';
import { PostUpdateResponse } from '../../responses/post/post-update.response';
import { environment } from 'src/app/environments/environment';
import { Media } from '../../models/media.model';
import { UpdatePostDTO } from '../../dtos/update-post.dto';

@Component({
  selector: 'app-post-form',
  templateUrl: './post-form.component.html',
  styleUrl: './post-form.component.css'
})
export class PostFormComponent implements OnInit {
  title = input.required<string>();
  type = input.required<'create' | 'edit'>();
  postId = input<number>();
// ArrayBuffer la kieu du lieu dung de luu tru du lieu nhi phan
  selectedMediaList: { id: number; type: "image" | "video"; src: string | ArrayBuffer | null; file: File | null }[] = [];
  selectedLength = this.selectedMediaList.length;
  closePostForm = output<void>();
  postService = inject(PostService);
  createPostForm!: FormGroup;
  updatePostForm!: FormGroup;
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
  removeMedia(): void {
    this.selectedMediaList = [];
    // this.cdRef.detectChanges();
  }
  onFilesSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
        const files = Array.from(input.files); // Chuyển FileList thành mảng

        if(this.type() === 'create'){
          this.selectedMediaList = []; // Khởi tạo danh sách tệp đã chọn
        }

        files.forEach((file) => {
            const reader = new FileReader();
            reader.onload = (e) => {
                const fileType = file.type.startsWith('image') ? 'image' : file.type.startsWith('video') ? 'video' : null;
                if (fileType) {
                    this.selectedMediaList.push({
                        id: 0,
                        type: fileType,
                        src: e.target?.result || null, // Dữ liệu để hiển thị preview
                        file: file, // Đối tượng File gốc
                    });
                }
                this.cdRef.detectChanges(); // Buộc Angular kiểm tra lại và cập nhật DOM
              };
              console.log('Selected media:', this.selectedMediaList);
            reader.readAsDataURL(file);
        });
    }

}


  onClosePostForm(): void {
    this.closePostForm.emit();
  }

  ngOnInit(): void {
    if(this.type() === 'create'){    
      this.initCreatePostFrom();
    }else if(this.type() === 'edit'){
      this.initUpdatePostForm();
    }
  }
  initCreatePostFrom(){
    this.createPostForm = new FormGroup({
      content: new FormControl('', [Validators.maxLength(1000)]),
      privacy: new FormControl('public', [Validators.required]),
    });
  }
  initUpdatePostForm(){
    this.selectedMediaList = [];
    this.updatePostForm = new FormGroup({
      content: new FormControl('', [Validators.maxLength(1000)]),
      privacy: new FormControl('', [Validators.required]),
    });
    this.postService.loadPostResponseById(this.postId()!).subscribe({
      next: (response: ApiResponse) => {          
        const post = response.data as PostUpdateResponse;
        this.updatePostForm.patchValue({
          content: post.content,
          privacy: post.privacy,
        });          
        post.medias.forEach((media: Media) => {
          if (media.media_type === 'image') {
            media.url = `${environment.apiBaseUrl}/medias/image_post/${media.url}`;
          } else if (media.media_type === 'video') {
            media.url = `${environment.apiBaseUrl}/medias/video_post/${media.url}`;
          }
          this.selectedMediaList.push({
            id: media.media_id,
            type: media.media_type,
            src: media.url,
            file: 'null' as unknown as File, // Để tránh lỗi khi không có file
          });
        });
        this.cdRef.detectChanges();
      },
      error: (error) => {
        console.error('Lỗi khi tải bài viết:', error);
      }
    });
  }

  onSubmit(){
    if(this.type() === 'create'){
      this.createPost();
    }else{
      this.updatePost();
    }
  }
  createPost(){
    if(this.createPostForm.valid){
      // Gửi dữ liệu lên server
      const formData = {
        author_id: this.tokenService.getProfileId(), 
        author_type: "profile",
        post_type: "normal",
        privacy: this.createPostForm.value.privacy,
        content: this.createPostForm.value.content
      };
      const createPostDTO = new CreatePostDTO(formData);
      this.postService.createPost(createPostDTO).subscribe({
        next: (response: ApiResponse) => {
          const postId = response.data.id;
          const files = this.selectedMediaList
          .map((media) => media.file)
          .filter((file): file is File => file !== null); // Lấy danh sách File gốc và loại bỏ null
          
          this.postService.uploadMedia(postId, files).subscribe({
            next: (response: ApiResponse) => {
              alert(response.message);
              this.onClosePostForm();
              console.log('Create post response:', response);
              
              window.location.reload();
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
  updatePost(){
    if(this.updatePostForm.valid){
      // Gửi dữ liệu lên server
      debugger;
      const formData = {
        content: this.updatePostForm.value.content,
        privacy: this.updatePostForm.value.privacy
      };
      const updatePostDTO = new UpdatePostDTO(formData);
      updatePostDTO.media_ids = this.selectedMediaList.map((media) => media.id);
      this.postService.updatePost(this.postId()!, updatePostDTO).subscribe({
        next: (response: ApiResponse) => {
          const postId = response.data.id;
          const files = this.selectedMediaList
          .map((media) => media.file)
          .filter((file): file is File => file !== null); // Lấy danh sách File gốc và loại bỏ null
          
          this.postService.uploadMedia(postId, files).subscribe({
            next: (response: ApiResponse) => {
              alert(response.message);
              this.onClosePostForm();
              window.location.reload();
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
