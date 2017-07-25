<?php
/**
 * The header for our theme
 *
 * This is the template that displays all of the <head> section and everything up until <div id="content">
 *
 * @package HumineCraft_Theme
 * @subpackage HumineCraft_Theme
 * @since 1.0
 * @version 2.0
 */

?><!DOCTYPE html>
<html <?php language_attributes(); ?> class="no-js no-svg">
<head>
<meta charset="<?php bloginfo( 'charset' ); ?>">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="profile" href="http://gmpg.org/xfn/11">

<?php wp_head(); ?>
<meta property="og:url" content="http://huminecraft.com" />
	<meta property="og:type" content="website" />
	<meta property="og:title" content="HumineCraft - Construisez votre monde Minecraft" />
	<meta property="og:description" content="Construisez, explorez, combattez et bâtissez votre royaume ! Serveur whitelisté." />
	<meta property="og:image" content="http://huminecraft.com/wp-content/uploads/2017/02/Logo-Huminecraft-miniature.png" />
</head>

<body <?php body_class(); ?>>
<div id="page" class="site">
	<a class="skip-link screen-reader-text" href="#content"><?php _e( 'Skip to content', 'huminecraft_theme' ); ?></a>

	<header id="masthead" class="site-header" role="banner">

		<?php get_template_part( 'template-parts/header/header', 'image' ); ?>

		<?php if ( has_nav_menu( 'top' ) ) : ?>
			<div class="navigation-top">
				<!--<div class="logo_wrap">
					<a href="http://huminecraft.com"><img src="http://hugobazin.com/HumineCraft/wp-content/uploads/2017/01/logo-copie.png"></a>
				</div>-->
				<div class="wrap">
					<?php get_template_part( 'template-parts/navigation/navigation', 'top' ); ?>
				</div><!-- .wrap -->
			</div><!-- .navigation-top -->
		<?php endif; ?>

	</header><!-- #masthead -->
	<div class="site-content-contain">
		<div id="content" class="site-content">
