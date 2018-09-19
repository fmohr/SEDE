/* im.h */

#ifndef __IM_H__
#define __IM_H__

#include <magick/MagickCore.h>

#ifdef OLD_IM_LIB
#define MagickCoreGenesis(argv, m) \
		InitializeMagick(argv)
#define MagickCoreTerminus() \
		DestroyMagick()
#define GetAuthenticPixels(image, x, y, columns, rows, exception) \
		GetImagePixels(image, x, y, columns, rows)
#define SyncAuthenticPixels(image, exception) \
		SyncImagePixels(image)
#endif

typedef struct __im {
	Image			*image;
	ImageInfo		*image_info;
	ExceptionInfo	*image_exception;
	size_t			 rows;
	size_t			 columns;
} IMAGE;

#endif /* __IM_H__ */
