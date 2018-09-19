
// MagickCore equivalent of
// convert imgin: imgin.jpg
// #include <magick/MagickCore.h>

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

// #include "service_sobel.h"


int main(int argc,char **argv)
{
	printf("ja\n");

	// // Image *image,*imagew;
	// // ImageInfo *read_info;
	// // ImageInfo *write_info;
	// // ExceptionInfo *exception;
	// // MagickBooleanType status;

	// const char *filename_target = argv[2];
	// const char *filename_source = argv[1];
	// double param_A;


	// int status			= 0;
	
	// // void *handler		= NULL;
	// // void *run_service	= NULL;
	
	// IMAGE *image_source			= NULL;
	// IMAGE *image_target			= NULL;
	// RAW_IMAGE *raw_image_source	= NULL;
	// RAW_IMAGE *raw_image_target	= NULL;
	
	// im_environment_up();
	
	// im_open_image(&image_source, filename_source);
	// im_open_image(&image_target, filename_target);
	
	// // typedef struct __image {
	// // 	Image			*image;
	// // 	ImageInfo		*image_info;
	// // 	ExceptionInfo	*image_exception;
	// // } IMAGE;

	// // typedef struct __raw_image {
	// // 	PixelPacket	*pixpack;
	// // 	size_t		rows;
	// // 	size_t		columns;
	// // } RAW_IMAGE;


	// // ---

	// // #define QuantumRange UINT16_MAX

	// // typedef uint16_t Quantum;

	// // typedef struct __PixelPacket__ {
	// // 	Quantum blue;
	// // 	Quantum green;
	// // 	Quantum red;
	// // 	Quantum opacity;
	// // } PixelPacket;

	// // typedef struct __Image__ {
	// // 	PixelPacket *image;
	// // 	int32_t rows;
	// // 	int32_t columns;
	// // 	int64_t j_arr;
	// // } Image;

	// im_read_image(image_source, &raw_image_source);
	// size_t rows_source		= im_get_image_rows(image_source);
	// size_t columns_source	= im_get_image_columns(image_source);

	// printf("source image, rows = %ld, cols = %ld\n", rows_source, columns_source);

	// im_create_image(image_target, &raw_image_target, rows_source, columns_source);
	
	// // run_service_sobel_scpu(PixelPacket *pixpack_target, PixelPacket *pixpack_source, int32_t rows, int32_t columns)
	// // run_service_sobel_scpu(raw_image_target->pixpack, raw_image_source->pixpack, rows_source, columns_source);

	// Image *image_image_source = image_source->image;

	// image_image_source->image->red;

	// // void run_service(char resource, void *handler_gpu, Image **target_images, int32_t *target_images_size, 
	// // Image *source_images, int32_t source_images_size, double *params, int32_t *status_code) {



	// // run_service('s', NULL, image_target, &target_images_size, image_source, source_images_size, &params, &status_code);

	// // // Realloc size of raw images to fit burstsize of FPGA.
	// // const size_t burst_size	= 384;
	// // size_t old_size			= raw_image_source->rows * raw_image_source->columns * sizeof(PixelPacket);
	// // size_t new_size			= (((old_size-1)/burst_size)+1)*burst_size;
	
	// // im_realloc_image(raw_image_source, new_size);
	// // im_realloc_image(raw_image_target, new_size);
	
	// // handler		= open_library(config->libraryname);
	// // run_service	= load_function(config->functionname, handler);
	
	// // struct timespec start;
	// // struct timespec stop;
	// // struct timespec diff;
	// // double time_diff		= -1.0;
	// // double time_predict		= -1.0;
	
	// // run_service_no_args_t rs		= NULL;
	// // run_service_float_t rs_float	= NULL;
	// // run_service_int32_t rs_int32	= NULL;
	
	// // DATA_NIL *data_nil				= NULL;
	// // double linreg_a					= 0.0;
	// // double linreg_b					= 0.0;
	
	// // DATA_INT32 *data_int32			= NULL;
	// // double coeffcients_vector[3]	= {0, 0, 0};
	
	// // //run the algorithm
	// // if (config->flag_linreg) {
	// // 	trainer_load_int32(config->trainingname, &data_int32);
	// // 	linreg_load_int32(config->linregname, data_int32, coeffcients_vector);
	// // 	trainer_free_int32(&data_int32);
		
	// // 	linreg_predict_int32(&time_predict, coeffcients_vector, rows_source*columns_source, (int32_t)param_A);
	// // }
	
	// // rs_int32 = (run_service_int32_t) run_service;
	
	// // clock_gettime(CLOCK_REALTIME, &start);
	// // status = rs_int32(raw_image_target->pixpack, raw_image_source->pixpack,
	// // 				rows_source, columns_source,
	// // 				(int32_t)param_A);
	// // clock_gettime(CLOCK_REALTIME, &stop);
			
	
	// // close_library(&handler);
	
	// im_destroy_image(&raw_image_source);
	// im_write_image(image_target, raw_image_target);
	// im_destroy_image(&raw_image_target);
	
	// im_close_image(&image_source);
	// im_close_image(&image_target);
	
	// im_environment_down();
	
	

	return 0;
}
