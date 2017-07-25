<?php
/**
 *
 * @package HumineCraft Theme
 * @subpackage HumineCraft Theme
 * @since 1.0
 * @version 2.0
 */
?>

<?php $unique_id = esc_attr( uniqid( 'search-form-' ) ); ?>

<form role="search" method="get" class="search-form" action="<?php echo esc_url( home_url( '/' ) ); ?>">
	<label for="<?php echo $unique_id; ?>">
		<span class="screen-reader-text"><?php echo _x( 'Recherche', 'label', 'huminecraft_theme' ); ?></span>
	</label>
	<input type="search" id="<?php echo $unique_id; ?>" class="search-field" placeholder="<?php echo esc_attr_x( 'Rechercher &hellip;', 'placeholder', 'huminecraft_theme' ); ?>" value="<?php echo get_search_query(); ?>" name="s" />
	<button type="submit" class="search-submit"><?php echo huminecraft_theme_get_svg( array( 'icon' => 'search' ) ); ?><span class="screen-reader-text"><?php echo _x( 'Go', 'submit button', 'huminecraft_theme' ); ?></span></button>
</form>
