import { Routes } from "@angular/router";
import { ProfilePostsComponent } from "./components/profile-posts/profile-posts.component";
import { ProfileFriendsComponent } from "./components/profile-friends/profile-friends.component";
import { ProfileAboutComponent } from "./components/profile-about/profile-about.component";

export const profileRoutes: Routes = [
    {
        path: '',
        component: ProfilePostsComponent
    },
    {
        path: 'friends',
        component: ProfileFriendsComponent
    },
    {
        path: 'about',
        component: ProfileAboutComponent
    },
];