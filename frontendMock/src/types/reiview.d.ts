export interface IReview {
  id: number;
  title: string;
  content: string;
  status?: boolean;
  rating: number;
  laptop_Id: number;
  user_Id: number;
}
