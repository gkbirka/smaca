SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smaca`
--
CREATE DATABASE IF NOT EXISTS `smaca` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `smaca`;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_name`) VALUES
('Drinks'),
('Refreshments'),
('Snacks');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `user_epc` varchar(30) NOT NULL,
  `order_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `order_id` int(11) NOT NULL,
  `product_epc` varchar(30) NOT NULL,
  `product_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_epc` varchar(30) NOT NULL,
  `product_name` varchar(30) NOT NULL,
  `category_name` varchar(30) NOT NULL,
  `product_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_epc`, `product_name`, `category_name`, `product_price`) VALUES
('2008 1016 0000 ABCD FFFF 0001', 'Lay\'s (90g)', 'Snacks', 0.9),
('3008 33B2 DDD9 0480 3505 0000', 'Coca - Cola (2L)', 'Refreshments', 2.53),
('3500 0000 1000 0030 0000 0054', 'Cheetos (125g)', 'Snacks', 1.25),
('3504 F6EB D011 27C0 0000 0000', 'Absolut Vodka (700ml)', 'Drinks', 16.13),
('3504 F6EB D013 A840 0000 00BB', 'Oreo (154g)', 'Snacks', 1.09);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_epc` varchar(30) NOT NULL,
  `user_first_name` varchar(30) NOT NULL,
  `user_last_name` varchar(30) NOT NULL,
  `user_pin` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_epc`, `user_first_name`, `user_last_name`, `user_pin`) VALUES
('3501 2D68 7000 0020 0000 0000', 'Jack', 'Carver', 1111),
('3501 2D68 7000 0020 0000 0001', 'Gordon', 'Brown', 2222),
('3501 2D68 7000 0020 0000 0002', 'Bob', 'Smith', 3333);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_name`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `user_epc_constraint` (`user_epc`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD KEY `order_id_constraint` (`order_id`),
  ADD KEY `product_epc_constraint` (`product_epc`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_epc`),
  ADD KEY `category_name_constraint` (`category_name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_epc`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `user_epc_constraint` FOREIGN KEY (`user_epc`) REFERENCES `users` (`user_epc`) ON UPDATE CASCADE;

--
-- Constraints for table `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `order_id_constraint` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `product_epc_constraint` FOREIGN KEY (`product_epc`) REFERENCES `products` (`product_epc`) ON UPDATE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `category_name_constraint` FOREIGN KEY (`category_name`) REFERENCES `categories` (`category_name`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
