<?php
/**
 * The template for displaying 404 pages (not found)
 *
 * @package HumineCraft Theme
 * @subpackage HumineCraft Theme
 * @since 1.0
 * @version 2.0
 */

get_header(); ?>

<div class="wrap">
	<div id="primary" class="content-area">
		<main id="main" class="site-main" role="main">

			<section class="error-404 not-found">
				<header class="page-header">
					<h1 class="page-title"><?php _e( 'Oh oh, il semble que vous ayez trop miné et que vous vous soyez retrouvé trop loin de votre base !', 'huminecraft_theme' ); ?></h1>
					<a href="http://huminecraft.com" class="btn_404">RETOURNER AU SPAWN</a>
				</header><!-- .page-header -->
				<div class="page-content">
					<img src="http://hugobazin.com/HumineCraft/wp-content/uploads/2017/01/404.png" class="img_404">
					<p></p>

					<?php get_search_form(); ?>

				</div><!-- .page-content -->
			</section><!-- .error-404 -->
		</main><!-- #main -->
	</div><!-- #primary -->
</div><!-- .wrap -->

<?php get_footer();
