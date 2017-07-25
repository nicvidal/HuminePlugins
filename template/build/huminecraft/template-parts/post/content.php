<?php
/**
 * Template part for displaying posts
 *
 * @link https://codex.wordpress.org/Template_Hierarchy
 *
 * @package HumineCraft_Theme
 * @subpackage HumineCraft_Theme
 * @since 1.0
 * @version 2.0
 */

?>

<article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
	<?php
		if ( is_sticky() && is_home() ) :
			echo huminecraft_theme_get_svg( array( 'icon' => 'thumb-tack' ) );
		endif;
	?>
	
	<header class="entry-header">
		<?php
			if ( is_single() ) {
				the_title( '<h1 class="entry-title">', '</h1>' );
			} else {
				the_title( '<h2 class="entry-title"><a href="' . esc_url( get_permalink() ) . '" rel="bookmark">', '</a></h2>' );
			}
			if ( 'post' === get_post_type() ) :
				echo '<div class="entry-meta">';
					if ( is_single() ) :
						huminecraft_theme_posted_on();
					else :
						echo huminecraft_theme_time_link();
						huminecraft_theme_edit_link();
					endif;
				echo '</div><!-- .entry-meta -->';
			endif;
		?>
	</header><!-- .entry-header -->
	<?php
	if ( has_post_thumbnail() && ( is_single() || ( is_page() && ! huminecraft_theme_is_frontpage() ) ) ) :
		echo '<div class="single-featured-image-header">';
		the_post_thumbnail( 'huminecraft_theme-featured-image' );
		echo '</div><!-- .single-featured-image-header -->';
	endif;
	?>
	<div class="entry-content">
		<?php
			/* translators: %s: Name of current post */
			the_content( sprintf(
				__( 'Continue reading<span class="screen-reader-text"> "%s"</span>', 'twentyseventeen' ),
				get_the_title()
			) );

			wp_link_pages( array(
				'before'      => '<div class="page-links">' . __( 'Pages:', 'twentyseventeen' ),
				'after'       => '</div>',
				'link_before' => '<span class="page-number">',
				'link_after'  => '</span>',
			) );
		?>
	</div><!-- .entry-content -->

	<?php if ( is_single() ) : ?>
		<?php huminecraft_theme_entry_footer(); ?>
	<?php endif; ?>

</article><!-- #post-## -->
