import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UserComponent} from './components/user/user.component';
import {PostComponent} from './components/post/post.component';
import {CommentComponent} from './components/comment/comment.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HomeComponent} from './home/home.component';
import {NotfoundComponent} from './notfound/notfound.component';
import {MessagetestComponent} from './messagetest/messagetest.component';
import {HeaderComponent} from './header/header.component';
import {LoginComponent} from './login/login.component';
import {SignupComponent} from './signup/signup.component';
import {QuestionComponent} from './question/question.component';
import {PostDetailsComponent} from './post-details/post-details.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from '@angular/common/http';
import {ToastrModule} from 'ngx-toastr';
import {JwtClientService} from "./services/jwt-client.service";
import {CookieService} from 'ngx-cookie-service';
import {SignoutComponent} from './signout/signout.component';
import {httpInterceptorProviders} from "./services";
import { CreatePostComponent } from './create-post/create-post.component';
import { CommentCountPipe } from './pipe/commentCount';
import { CreateCommentComponent } from './create-comment/create-comment.component';
import { ReplyComponent } from './reply/reply.component'

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    PostComponent,
    CommentComponent,
    HomeComponent,
    NotfoundComponent,
    MessagetestComponent,
    HeaderComponent,
    LoginComponent,
    SignupComponent,
    QuestionComponent,
    PostDetailsComponent,
    SignoutComponent,
    CreatePostComponent,
    CommentCountPipe,
    CreateCommentComponent,
    ReplyComponent
  ],
  imports: [
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [JwtClientService, CookieService, httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
