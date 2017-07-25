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
<nav id="site-navigation" class="main-navigation" role="navigation" aria-label="<?php _e( 'Bottom Menu', 'huminecraft_theme' ); ?>">
	<button class="menu-toggle" aria-controls="bottom-menu" aria-expanded="false"><?php echo huminecraft_theme_get_svg( array( 'icon' => 'bars' ) ); echo huminecraft_theme_get_svg( array( 'icon' => 'close' ) ); _e( 'Menu', 'huminecraft_theme' ); ?></button>
	<?php wp_nav_menu( array(
		'theme_location' => 'bottom',
		'menu_id'        => 'bottom-menu',
	) ); ?>
</nav><!-- #site-navigation -->
