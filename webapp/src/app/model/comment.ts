import { User } from "./user";

export class Comment {
    id: string = '';
    message: string = '';
    author: User = new User();
    date: string = '';
    reply: Comment[] = [];
}