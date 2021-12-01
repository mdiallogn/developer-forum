import { Comment } from "./comment";
import { User } from "./user";

export class Post {
    id: string = '';
    subject: string = '';
    content: string = '';
    author: User = new User();
    date: string = '';
    comments: Comment[] = [];
    upVote:number = 0;
    downVote:number = 0;
}