import { ActivatedRouteSnapshot, ResolveFn, Router, RouterStateSnapshot, Routes } from "@angular/router";
import { ProfilePostsComponent } from "./components/profile-posts/profile-posts.component";
import { ProfileFriendsComponent } from "./components/profile-friends/profile-friends.component";
import { ProfileAboutComponent } from "./components/profile-about/profile-about.component";
import { ProfilePhotosComponent } from "./components/profile-photos/profile-photos.component";
import { ProfileVideosComponent } from "./components/profile-videos/profile-videos.component";
import { ProfileMapComponent } from "./components/profile-map/profile-map.component";

export const profileRoutes: Routes =  [
    {
        path: 'posts',
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
    {
        path: 'photos',
        component: ProfilePhotosComponent
    },
    {
        path: 'videos',
        component: ProfileVideosComponent
    },
    {
        path: 'map',
        component: ProfileMapComponent
    },
  ];