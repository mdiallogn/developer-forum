import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { MessagetestComponent } from './messagetest/messagetest.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { PostDetailsComponent } from './post-details/post-details.component';
import { SignupComponent } from './signup/signup.component';
import { CreatePostComponent } from './create-post/create-post.component'

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'test', component: MessagetestComponent },
  { path: 'login', component: LoginComponent},
  { path: 'signup', component: SignupComponent},
  { path: 'posts/create', component: CreatePostComponent},
  { path: 'posts/:id', component: PostDetailsComponent},
  { path: '404', component: NotfoundComponent},
  { path: '',   redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '404' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
