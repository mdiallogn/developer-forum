import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserComponent } from './components/user/user.component';
import { PostComponent } from './components/post/post.component';
import { CommentComponent } from './components/comment/comment.component';
import {FormsModule} from "@angular/forms";
import { HomeComponent } from './home/home.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { MessagetestComponent } from './messagetest/messagetest.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { QuestionComponent } from './question/question.component';
import { PostDetailsComponent } from './post-details/post-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';

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
    PostDetailsComponent
  ],
  imports: [
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
