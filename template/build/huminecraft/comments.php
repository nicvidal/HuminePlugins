<?php
/**
 * The template for displaying comments
 *
 * This is the template that displays the area of the page that contains both the current comments
 * and the comment form.
 *
 * @package HumineCraft Theme
 * @subpackage HumineCraft Theme
 * @since 1.0
 * @version 2.0
 */

/*
 * If the current post is protected by a password and
 * the visitor has not yet entered the password we will
 * return early without loading the comments.
 */
if ( post_password_required() ) {
	return;
}
?>

<div id="comments" class="comments-area">

	<?php
	// You can start editing here -- including this comment!
	if ( have_comments() ) : ?>
		<h2 class="comments-title">
			<?php
				$comments_number = get_comments_number();
				if ( '1' === $comments_number ) {
					/* translators: %s: post title */
					printf( _x( 'Un Commentaire à l\'article &ldquo;%s&rdquo;', 'comments title', 'huminecraft_theme' ), get_the_title() );
				} else {
					printf(
						/* translators: 1: number of comments, 2: post title */
						_nx(
							'%1$s Commentaire à l’article &ldquo;%2$s&rdquo;',
							'%1$s Commentaires à l\'article &ldquo;%2$s&rdquo;',
							$comments_number,
							'comments title',
							'huminecraft_theme'
						),
						number_format_i18n( $comments_number ),
						get_the_title()
					);
				}
			?>
		</h2>

		<ol class="comment-list">
			<?php
				wp_list_comments( array(
					'avatar_size' => 100,
					'style'       => 'ol',
					'short_ping'  => true,
					'reply_text'  => huminecraft_theme_get_svg( array( 'icon' => 'mail-reply' ) ) . __( 'Reply', 'huminecraft_theme' ),
				) );
			?>
		</ol>

		<?php the_comments_pagination( array(
			'prev_text' => huminecraft_theme_get_svg( array( 'icon' => 'arrow-left' ) ) . '<span class="screen-reader-text">' . __( 'Précédent', 'huminecraft_theme' ) . '</span>',
			'next_text' => '<span class="screen-reader-text">' . __( 'Suivant', 'huminecraft_theme' ) . '</span>' . huminecraft_theme_get_svg( array( 'icon' => 'arrow-right' ) ),
		) );

	endif; // Check for have_comments().

	// If comments are closed and there are comments, let's leave a little note, shall we?
	if ( ! comments_open() && get_comments_number() && post_type_supports( get_post_type(), 'comments' ) ) : ?>

		<p class="no-comments"><?php _e( 'Comments are closed.', 'huminecraft_theme' ); ?></p>
	<?php
	endif;

	comment_form();
	?>

</div><!-- #comments -->
