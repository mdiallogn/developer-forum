// @ts-ignore
import {Pipe, PipeTransform} from '@angular/core';
import { Comment } from '../model/comment';

@Pipe({name: 'commentCount'})
export class CommentCountPipe implements PipeTransform {
  transform(value: Comment[]) {
      let count = 0;
      value.forEach(comment => {
          count += 1
          if (comment.reply != null) {
            count +=comment.reply.length
          }
      });
    return count;
  }
}
