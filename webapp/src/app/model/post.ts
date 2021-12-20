import { Comment } from "./comment";
import { User } from "./user";

export class Post {
    id: string = '';
    subject: string = '';
    content: string = '';
    author: User = new User();
    date: string = '';
    comments: Comment[] = [];
    upVoters:User[] = [];
    downVoters:User[] = [];
}