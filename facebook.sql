-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 29, 2024 at 02:07 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `facebook1`
--

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE `friends` (
  `friend_id` int(11) NOT NULL,
  `first_profile_id` int(11) NOT NULL,
  `second_profile_id` int(11) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`friend_id`, `first_profile_id`, `second_profile_id`, `is_active`) VALUES
(1, 1, 2, 1),
(2, 1, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `friend_requests`
--

CREATE TABLE `friend_requests` (
  `friend_request_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `status` enum('PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'PENDING'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `base_id` int(11) NOT NULL,
  `group_name` varchar(255) NOT NULL,
  `privacy` enum('public','private') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `interactions`
--

CREATE TABLE `interactions` (
  `interaction_id` int(11) NOT NULL,
  `post_media_id` int(11) NOT NULL,
  `post_media_type` enum('post','media') NOT NULL,
  `interactor_id` int(11) NOT NULL,
  `interactor_type` enum('profile','page') DEFAULT NULL,
  `interaction_type` enum('like','love','care','haha','wow','sad','angry') DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `interactions`
--

INSERT INTO `interactions` (`interaction_id`, `post_media_id`, `post_media_type`, `interactor_id`, `interactor_type`, `interaction_type`, `created_at`, `updated_at`) VALUES
(1, 19, 'post', 1, 'profile', 'haha', '2024-10-05 15:01:15', '2024-10-05 15:01:15'),
(6, 18, 'post', 2, 'page', 'like', '2024-10-05 15:02:52', '2024-10-05 15:02:52'),
(7, 19, 'post', 3, 'profile', 'like', '2024-10-05 15:02:52', '2024-10-05 15:02:52'),
(8, 18, 'post', 4, 'profile', 'love', '2024-10-05 15:02:52', '2024-10-05 15:02:52'),
(9, 8, 'post', 4, 'profile', 'sad', '2024-10-07 09:31:43', '2024-10-07 09:31:43'),
(10, 17, 'post', 2, 'profile', 'care', '2024-10-07 09:32:58', '2024-10-07 09:32:58'),
(11, 17, 'post', 1, 'profile', 'care', '2024-10-07 09:33:58', '2024-10-07 09:33:58'),
(14, 19, 'post', 2, 'profile', 'angry', '2024-10-15 23:50:39', '2024-10-15 23:50:39'),
(15, 19, 'post', 4, 'profile', 'sad', '2024-10-15 23:50:39', '2024-10-15 23:50:39'),
(16, 14, 'media', 2, 'profile', 'love', '2024-10-16 19:48:40', '2024-10-16 19:48:40');

-- --------------------------------------------------------

--
-- Table structure for table `medias`
--

CREATE TABLE `medias` (
  `media_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `note` longtext DEFAULT '',
  `url` varchar(350) NOT NULL,
  `media_type` enum('image','video') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medias`
--

INSERT INTO `medias` (`media_id`, `post_id`, `note`, `url`, `media_type`) VALUES
(10, 8, 'Đây là ảnh post 8', '7001cd4f-43e0-413b-89bc-4a5211d2bda3_abdul-mukheem-shaik-ytj13TMmn6Y-unsplash.jpg', 'image'),
(11, 9, 'Đây là video post 9', 'a5fb29da-3ecf-4082-9e6b-0cb140767a41_Components - Google Chrome 2023-07-30 16-03-14.mp4', 'video'),
(12, 11, 'Đây là video post 11', 'f6f50148-6820-4f49-b108-183c1fb4a052_ok.mp4', 'video'),
(13, 12, 'Đây là video post 12', '989cbab0-7e30-4b93-9a46-61fcdb44c864_ok.mp4', 'video'),
(14, 17, 'Đây là ảnh post 17', 'dec8d20f-4717-4f13-b221-e2b0e01c04f7_cris.jpg', 'image'),
(15, 18, 'Đây là ảnh post 18', '1dc9adca-30db-403a-9af4-078d48213755_vcs.jpg', 'image'),
(26, 19, 'Đây là ảnh post 19', '5827c941-9a5b-4e5a-a74e-0be82d9797b3_sanbong.jpg', 'image'),
(27, 19, 'Đây là ảnh post 19', '5827c941-9a5b-4e5a-a74e-0be82d9797b3_sanbong.jpg', 'image'),
(28, 16, NULL, 'e37feb59-894e-47eb-b4af-fa0b437523e5_video1.mp4', 'video'),
(29, 16, NULL, '800ee1f1-b3c9-4bc2-8c7a-5c2c407d79cb_video1.mp4', 'video'),
(30, 16, 'ok', 'ee981470-0da2-4be5-9182-532cf24c8fc5_video1.mp4', 'video');

-- --------------------------------------------------------

--
-- Table structure for table `pages`
--

CREATE TABLE `pages` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `base_id` int(11) NOT NULL,
  `page_name` varchar(255) NOT NULL,
  `page_type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pages`
--

INSERT INTO `pages` (`id`, `user_id`, `base_id`, `page_name`, `page_type`) VALUES
(1, 1, 4, 'Page cua The Hien', 'Nha sang tao noi dung'),
(2, 2, 5, 'Page cua My Duyen', 'Nha sang tao noi dung');

-- --------------------------------------------------------

--
-- Table structure for table `page_bases`
--

CREATE TABLE `page_bases` (
  `id` int(11) NOT NULL,
  `path_name` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `cover_photo` varchar(255) DEFAULT NULL,
  `biography` longtext NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp(),
  `is_active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `page_bases`
--

INSERT INTO `page_bases` (`id`, `path_name`, `avatar`, `cover_photo`, `biography`, `created_at`, `updated_at`, `is_active`) VALUES
(1, 'thehienn08', '2ad4207f-b3d4-45c0-8bb2-5ff0155dd421_thehien.jpg', 'd21902df-8fe7-43e2-b831-4b04f62ecf20_sanbong.jpg', 'I\'m The Hien', '2024-10-02', '2024-10-26', 1),
(2, 'my.duyene', '91d3ef09-5658-4aa2-8b73-406251fab8df_myduyen.jpg', '437996dd-adab-4b54-b35b-01185f206daf_nuiba.jpg', 'I\'m My Duyen', '2024-10-08', '2024-10-11', 1),
(3, '100090927905282', 'dd396930-9f74-418d-99de-10336339c43b_phanphung.jpg', '5a192439-3388-4deb-b112-e1fd182537ca_lobby.png', 'I\'m Phan Phung', '2024-10-06', '2024-10-26', 1),
(4, 'thehienpage', NULL, NULL, 'Page cua Hien', '2024-10-11', '2024-10-11', 1),
(5, 'myduyenpage', NULL, NULL, 'Page cua Duyen', '2024-10-11', '2024-10-11', 1),
(22, 'admin', '', '', '', '2024-10-11', '2024-10-11', 1);

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE `posts` (
  `id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `author_type` enum('profile','page') DEFAULT NULL,
  `post_type` enum('normal','avatar','cover_photo') NOT NULL,
  `privacy` enum('public','friends','only me') DEFAULT NULL,
  `content` longtext NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp(),
  `is_active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`id`, `author_id`, `author_type`, `post_type`, `privacy`, `content`, `created_at`, `updated_at`, `is_active`) VALUES
(1, 1, 'profile', 'normal', 'friends', 'Hom nay troi that dep qua 0', '2024-09-26 02:50:54', '2024-09-25 05:37:23', 1),
(8, 2, 'page', 'normal', 'public', 'Hom nay troi that dep 9', '2024-09-27 06:13:56', '2024-09-25 06:15:56', 1),
(9, 4, 'profile', 'normal', 'friends', 'Hom nay troi that dep 4', '2024-09-25 07:56:14', '2024-09-26 06:19:38', 1),
(10, 3, 'profile', 'normal', 'friends', 'Hom nay troi that dep 3', '2024-09-25 15:53:20', '2024-09-25 15:53:20', 1),
(11, 3, 'profile', 'normal', 'friends', 'Hom nay troi that dep 25', '2024-09-25 16:14:28', '2024-09-25 16:14:28', 1),
(12, 1, 'profile', 'normal', 'friends', 'Hom nay troi that dep 25', '2024-09-25 16:15:11', '2024-09-25 16:15:11', 1),
(13, 1, 'profile', 'normal', 'friends', 'Hom nay troi that dep 25', '2024-09-25 16:15:54', '2024-09-25 16:15:54', 1),
(14, 1, 'profile', 'normal', 'friends', 'Hom nay troi that dep 25', '2024-09-25 16:16:53', '2024-09-25 16:16:53', 1),
(15, 1, 'profile', 'normal', 'friends', 'Hom nay troi that dep 11', '2024-09-25 16:18:27', '2024-09-25 16:18:27', 1),
(16, 1, 'profile', 'normal', 'public', 'Ngủ thôi', '2023-09-06 17:01:30', '2024-10-03 17:01:30', 1),
(17, 2, 'profile', 'normal', 'public', 'Anh em tui đến nè 👽', '2024-10-05 10:50:08', '2024-10-05 05:46:29', 1),
(18, 3, 'profile', 'normal', 'public', 'Danh sách tướng trong mắt các mid-lane 🥹\r\n_____________________________________________\r\nLịch thi đấu các trận BO3 Vòng Swiss nhánh 2-0 ngày 05.10:\r\n👉 19:00 DPlus KIA (LCK #3) vs LNG Ninebot Esports (LPL #3)\r\n👉 22:00 Hanwha Life Esports (LCK #1) vs Gen.G (LCK #2)\r\nTất cả các trận đấu sẽ được phát sóng trên kênh Youtube & Tiktok @vcslmht. Đừng bỏ lỡ bất kỳ diễn biến nào của CKTG 2024 anh em nhé!\r\n#VCSLMHT #CKTG2024 #Worlds2024', '2024-10-05 10:50:08', '2024-10-05 05:46:29', 1),
(19, 1, 'profile', 'normal', 'only me', 'ok ok', '2024-10-05 11:12:13', '2024-10-05 11:12:13', 1),
(20, 1, 'profile', 'normal', 'friends', 'Ngủ đi', '2024-10-05 04:15:39', '2024-10-05 04:15:39', 1),
(21, 1, 'profile', 'normal', 'friends', 'Ngủ đi bà nội', '2024-10-11 16:28:25', '2024-10-11 16:28:25', 1);

-- --------------------------------------------------------

--
-- Table structure for table `profiles`
--

CREATE TABLE `profiles` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `base_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `display_format` enum('firstname_lastname','lastname_firstname') NOT NULL DEFAULT 'lastname_firstname',
  `gender` enum('male','female') NOT NULL,
  `date_of_birth` date NOT NULL,
  `is_online` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profiles`
--

INSERT INTO `profiles` (`id`, `user_id`, `base_id`, `first_name`, `last_name`, `display_format`, `gender`, `date_of_birth`, `is_online`) VALUES
(1, 1, 1, 'Hiển', 'Thế', 'lastname_firstname', 'male', '2003-05-22', 0),
(2, 2, 2, 'Duyên', 'Mỹ', 'lastname_firstname', 'female', '2024-10-10', 0),
(3, 3, 3, 'Phụng', 'Phan', 'lastname_firstname', 'male', '1997-10-10', 0),
(4, 48, 22, 'Vien', 'Quan Tri', 'lastname_firstname', 'male', '1997-10-10', 0);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`role_id`, `role_name`) VALUES
(1, 'USER'),
(2, 'ADMIN'),
(3, 'PAGE');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `password` varchar(200) NOT NULL,
  `role_id` int(11) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp(),
  `is_active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `password`, `role_id`, `phone_number`, `created_at`, `updated_at`, `is_active`) VALUES
(1, '$2a$10$pBGfQmsYrX7X2VvxDpjIS.mf63Ek.ck.MmQneQQlJufY5Sp.rbweW', 1, '0778689851', '2024-10-10 13:18:14', '2024-10-10 13:18:14', 1),
(2, '$2a$10$pBGfQmsYrX7X2VvxDpjIS.mf63Ek.ck.MmQneQQlJufY5Sp.rbweW', 1, '0778689852', '2024-10-10 13:18:30', '2024-10-10 13:18:30', 1),
(3, '$2a$10$pBGfQmsYrX7X2VvxDpjIS.mf63Ek.ck.MmQneQQlJufY5Sp.rbweW', 1, '0925252525', '2024-10-11 10:14:06', '2024-10-11 10:14:06', 1),
(4, '$2a$10$ZPunuZ7ZyiDV8HNk2uoMRuY1P9PuAOwKyImXHm97qA1rOEILi238K', 2, '0909090909', '2024-10-11 15:29:30', '2024-10-11 15:29:30', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `friends`
--
ALTER TABLE `friends`
  ADD PRIMARY KEY (`friend_id`);

--
-- Indexes for table `friend_requests`
--
ALTER TABLE `friend_requests`
  ADD PRIMARY KEY (`friend_request_id`);

--
-- Indexes for table `interactions`
--
ALTER TABLE `interactions`
  ADD PRIMARY KEY (`interaction_id`),
  ADD UNIQUE KEY `unique_post_reactable` (`post_media_id`,`interactor_id`,`interactor_type`);

--
-- Indexes for table `medias`
--
ALTER TABLE `medias`
  ADD PRIMARY KEY (`media_id`);

--
-- Indexes for table `pages`
--
ALTER TABLE `pages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `page_bases`
--
ALTER TABLE `page_bases`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `profiles`
--
ALTER TABLE `profiles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `friends`
--
ALTER TABLE `friends`
  MODIFY `friend_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `friend_requests`
--
ALTER TABLE `friend_requests`
  MODIFY `friend_request_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `interactions`
--
ALTER TABLE `interactions`
  MODIFY `interaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `medias`
--
ALTER TABLE `medias`
  MODIFY `media_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `pages`
--
ALTER TABLE `pages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `page_bases`
--
ALTER TABLE `page_bases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `profiles`
--
ALTER TABLE `profiles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
