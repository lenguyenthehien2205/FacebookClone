import { ActivatedRouteSnapshot, ResolveFn, Router, RouterStateSnapshot, Routes } from "@angular/router";
import { ProfilePostsComponent } from "./components/profile-posts/profile-posts.component";
import { ProfileFriendsComponent } from "./components/profile-friends/profile-friends.component";
import { ProfileAboutComponent } from "./components/profile-about/profile-about.component";
import { ProfilePhotosComponent } from "./components/profile-photos/profile-photos.component";
import { ProfileVideosComponent } from "./components/profile-videos/profile-videos.component";
import { ProfileMapComponent } from "./components/profile-map/profile-map.component";
import { OverviewComponent } from "./components/profile-about/overview/overview.component";
import { WorkComponent } from "./components/profile-about/work/work.component";
import { PlaceComponent } from "./components/profile-about/place/place.component";
import { InfoComponent } from "./components/profile-about/info/info.component";

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
        component: ProfileAboutComponent,
        children: [
            {
                path: 'overview',
                component: OverviewComponent
            },
            {
                path: 'work',
                component: WorkComponent
            },
            {
                path: 'place',
                component: PlaceComponent
            },
            {
                path: 'info',
                component: InfoComponent
            },
        ]
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