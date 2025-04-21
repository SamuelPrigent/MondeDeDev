export interface Article {
  id: number;
  title: string;
  description: string;
  theme: string;
  authorId: number;
  authorUsername: string;
  comments: Comment[];
  created_at: string;
  updated_at: string;
}

interface Comment {
  userId: number;
  comment: string;
}
