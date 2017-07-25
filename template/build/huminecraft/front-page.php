<?php
/**
 * The front page template file
 *
 * @package HumineCraft Theme
 * @subpackage HumineCraft Theme
 * @since 1.0
 * @version 2.0
 */

get_header(); ?>

<div id="primary" class="content-area">
	<main id="main" class="site-main" role="main">

		<?php
		if ( have_posts() ) :
			while ( have_posts() ) : the_post();
				get_template_part( 'template-parts/page/content', 'front-page' );
			endwhile;
		else : 
			get_template_part( 'template-parts/post/content', 'none' );
		endif; ?>

		<?php
		// Get each of our panels and show the post data.
		if ( 0 !== huminecraft_theme_panel_count() || is_customize_preview() ) : // If we have pages to show.

			/**
			 *
			 * @param $num_sections integer
			 */
			$num_sections = apply_filters( 'huminecraft_theme_front_page_sections', 4 );
			global $huminecraft_themecounter;

			// Create a setting and control for each of the sections available in the theme.
			for ( $i = 1; $i < ( 1 + $num_sections ); $i++ ) {
				$huminecraft_themecounter = $i;
				huminecraft_theme_front_page_section( null, $i );
			}

	endif; // The if ( 0 !== huminecraft_theme_panel_count() ) ends here. ?>

	</main><!-- #main -->
</div><!-- #primary -->

<?php get_footer();
