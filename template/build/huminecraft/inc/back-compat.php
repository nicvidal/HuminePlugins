<?php
/**
 *
 * @package HumineCraft_Theme
 * @subpackage HumineCraft_Theme
 * @since 1.0
 * @version 2.0
 */

/**
 * Prevent switching to this version on old versions of WordPress.
 *
 * Switches to the default theme.
 *
 */
function huminecraft_theme_switch_theme() {
	switch_theme( WP_DEFAULT_THEME );
	unset( $_GET['activated'] );
	add_action( 'admin_notices', 'huminecraft_theme_upgrade_notice' );
}
add_action( 'after_switch_theme', 'huminecraft_theme_switch_theme' );

/**
 * Adds a message for unsuccessful theme switch.
 *
 * @global string $wp_version WordPress version.
 */
function huminecraft_theme_upgrade_notice() {
	$message = sprintf( __( 'HumineCraft Theme requires at least WordPress version 4.7. You are running version %s. Please upgrade and try again.', 'huminecraft_theme' ), $GLOBALS['wp_version'] );
	printf( '<div class="error"><p>%s</p></div>', $message );
}

/**
 * Prevents the Customizer from being loaded on WordPress versions prior to 4.7.
 *
 * @global string $wp_version WordPress version.
 */
function huminecraft_theme_customize() {
	wp_die( sprintf( __( 'HumineCraft Theme requires at least WordPress version 4.7. You are running version %s. Please upgrade and try again.', 'huminecraft_theme' ), $GLOBALS['wp_version'] ), '', array(
		'back_link' => true,
	) );
}
add_action( 'load-customize.php', 'huminecraft_theme_customize' );

/**
 * Prevents the Theme Preview from being loaded on WordPress versions prior to 4.7.
 *
 * @global string $wp_version WordPress version.
 */
function huminecraft_theme_preview() {
	if ( isset( $_GET['preview'] ) ) {
		wp_die( sprintf( __( 'HumineCraft Theme requires at least WordPress version 4.7. You are running version %s. Please upgrade and try again.', 'huminecraft_theme' ), $GLOBALS['wp_version'] ) );
	}
}
add_action( 'template_redirect', 'huminecraft_theme_preview' );
