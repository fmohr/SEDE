#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <wand/MagickWand.h>

#define ThrowWandException(wand) \
{ \
  char \
    *description; \
 \
  ExceptionType \
    severity; \
 \
  description=MagickGetException(wand,&severity); \
  (void) fprintf(stderr,"%s %s %lu %s\n",GetMagickModule(),description); \
  description=(char *) MagickRelinquishMemory(description); \
  exit(-1); \
}

int main(int argc,char **argv)
{

  MagickBooleanType
    status;

  MagickPixelPacket
    pixel,
    contrast_pixel;

  MagickWand
    *contrast_wand,
    *image_wand;

  PixelIterator
    *contrast_iterator,
    *iterator;

  PixelWand
    **contrast_pixels,
    **pixels;

  register ssize_t
    x;

  size_t
    width;

  ssize_t
    y;


  /*
    Read an image.
  */
  MagickWandGenesis();
  image_wand=NewMagickWand();
  status=MagickReadImage(image_wand,argv[1]);

  if (status == MagickFalse)
    ThrowWandException(image_wand);
  contrast_wand=CloneMagickWand(image_wand);

  /*
    Sigmoidal non-linearity contrast control.
  */
  iterator=NewPixelIterator(image_wand);

  contrast_iterator=NewPixelIterator(contrast_wand);
  
  if ((iterator == (PixelIterator *) NULL) ||
      (contrast_iterator == (PixelIterator *) NULL))
    ThrowWandException(image_wand);

  ssize_t image_height = (ssize_t) MagickGetImageHeight(image_wand);
  ssize_t image_width  = (ssize_t) MagickGetImageWidth(image_wand);


  printf("Image in: %ld x %ld ..\n", image_height, image_width);


  for (y=0; y < image_height; y++)
  {
    pixels=PixelGetNextIteratorRow(iterator, &width);

    contrast_pixels=PixelGetNextIteratorRow(contrast_iterator, &width);

    for (x=0; x < (ssize_t) width; x++)
    {
      PixelGetMagickColor(pixels[x],&pixel);
      PixelGetMagickColor(contrast_pixels[x],&contrast_pixel);

      pixel.red=pixel.red;
      pixel.green=pixel.green;
      pixel.blue=pixel.blue;
      pixel.index=pixel.index;

      if(y == 0 && x == 0) {
        printf("%f %f %f\n", contrast_pixel.red, contrast_pixel.green, contrast_pixel.blue);
      }

      // PixelSetMagickColor(contrast_pixels[x],&pixel);
    }
    (void) PixelSyncIterator(contrast_iterator);
  }
  if (y < (ssize_t) MagickGetImageHeight(image_wand))
    ThrowWandException(image_wand);


  contrast_iterator=DestroyPixelIterator(contrast_iterator);
  iterator=DestroyPixelIterator(iterator);


  /*
    Write the image then destroy it.
  */
  status=MagickWriteImages(contrast_wand,argv[2],MagickTrue);
  if (status == MagickFalse)
    ThrowWandException(image_wand);

  image_wand=DestroyMagickWand(image_wand);
  contrast_wand=DestroyMagickWand(contrast_wand);
  MagickWandTerminus();
  return(0);
}
