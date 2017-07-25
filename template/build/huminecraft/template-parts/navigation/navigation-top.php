<?php
/**
 * Displays top navigation
 *
 * @package HumineCraft_Theme
 * @subpackage HumineCraft_Theme
 * @since 1.0
 * @version 2.0
 */

?>
<nav id="site-navigation" class="main-navigation" role="navigation" aria-label="<?php _e( 'Top Menu', 'huminecraft_theme' ); ?>">
	<button class="menu-toggle" aria-controls="top-menu" aria-expanded="false"><?php echo huminecraft_theme_get_svg( array( 'icon' => 'bars' ) ); echo huminecraft_theme_get_svg( array( 'icon' => 'close' ) ); _e( 'Menu', 'huminecraft_theme' ); ?></button>
	<?php wp_nav_menu( array(
		'theme_location' => 'top',
		'menu_id'        => 'top-menu',
	) ); ?>

	<?php if ( ( huminecraft_theme_is_frontpage() || ( is_home() && is_front_page() ) ) && has_custom_header() ) : ?>
		<a href="#content" class="menu-scroll-down"><?php echo huminecraft_theme_get_svg( array( 'icon' => 'arrow-right' ) ); ?><span class="screen-reader-text"><?php _e( 'Scroll down to content', 'huminecraft_theme' ); ?></span></a>
	<?php endif; ?>
</nav><!-- #site-navigation -->
