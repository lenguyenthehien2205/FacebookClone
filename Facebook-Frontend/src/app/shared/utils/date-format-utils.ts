export function getDayOfWeek(date: Date): string {
    const daysOfWeek = ["Chủ Nhật", "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy"];
    return daysOfWeek[date.getDay()];
  }
  // console.log(this.userResponse?.date_of_birth.toISOString().substring(0, 10));
// Output: "1990-05-15"
  
  export function formatDate(inputDate: number[]): string {
    const createdAtDate = new Date(
      inputDate[0],
      inputDate[1] - 1,
      inputDate[2],
      inputDate[3],
      inputDate[4],
      inputDate[5]
    );
    const day = getDayOfWeek(createdAtDate);
    const date = createdAtDate.getDate().toString();
    const month = (createdAtDate.getMonth() + 1).toString();
    const year = createdAtDate.getFullYear();
    const hour = createdAtDate.getHours().toString().padStart(2, '0');
    const minute = createdAtDate.getMinutes().toString().padStart(2, '0');
    return `${day}, ${date} Tháng ${month}, ${year} lúc ${hour}:${minute}`;
  }
  
  export function getTimeAgo(inputDate: number[]): string {
    const createdAtDate = new Date(
      inputDate[0],
      inputDate[1] - 1,
      inputDate[2],
      inputDate[3],
      inputDate[4],
      inputDate[5]
    );
    const date = createdAtDate.getDate().toString();
    const month = (createdAtDate.getMonth() + 1).toString();
    const year = createdAtDate.getFullYear();
    const hour = createdAtDate.getHours().toString().padStart(2, '0');
    const minute = createdAtDate.getMinutes().toString().padStart(2, '0');

    const now = new Date();
    const diff = Math.floor((now.getTime() - createdAtDate.getTime()) / 1000);
  
    switch (true) {
      case (diff < 60):
        return 'Vừa xong';
      case (diff < 3600):
        const minutes = Math.floor(diff / 60);
        return `${minutes} phút`;
      case (diff < 86400):
        const hours = Math.floor(diff / 3600);
        return `${hours} giờ`;
      case (diff < 604800):
        const days = Math.floor(diff / 86400);
        return `${days} ngày`;
      case (diff < 2592000):
        return `${date} Tháng ${month}, lúc ${hour}:${minute}`;
      case (now.getFullYear() > createdAtDate.getFullYear()):
        return `${date} Tháng ${month}, ${year}`;
      default:
        return `${date} Tháng ${month}`;
    }
  }

  export function getTimeAgoForConversation(inputDate: number[]): string {
    const createdAtDate = new Date(
      inputDate[0],
      inputDate[1] - 1,
      inputDate[2],
      inputDate[3],
      inputDate[4],
      inputDate[5]
    );
    const now = new Date();
    const diff = Math.floor((now.getTime() - createdAtDate.getTime()) / 1000);
  
    switch (true) {
      case diff < 60:
        return 'Vừa xong';
      case diff < 3600:
        return `${Math.floor(diff / 60)} phút`;
      case diff < 86400:
        return `${Math.floor(diff / 3600)} giờ`;
      case diff < 604800:
        return `${Math.floor(diff / 86400)} ngày`;
      case diff < 2592000:
        return `${Math.floor(diff / 604800)} tuần`;
      case diff < 31536000:
        const months = Math.floor(diff / 2592000);
        return `${months} tháng`;
      default:
        const years = Math.floor(diff / 31536000);
        return `${years} năm`;
    }
  }