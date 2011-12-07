package frappe.kobe;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Log;


public class ImageCompare {

 public static boolean imageMatch(Bitmap i, Bitmap i2) {
  
  int currentPixelValue, newPixelValue;
  int[][] imageArray = new int[224][224], 
		  imageArray2 = new int[224][224], 
		  lbpArray = new int[224][224], 
		  lbpArray2 = new int[224][224], 
		  histogram = new int[9][256], 
		  histogram2 = new int[9][256]; 
  
  int [][] unblurredImage = new int[224] [224];
  int [][] unblurredImage2 = new int[224] [224];
  
  //input pictures, resize to 224x224
  Bitmap image = Bitmap.createScaledBitmap(i, 224, 224, true);
  Bitmap image2 = Bitmap.createScaledBitmap(i2, 224, 224, true);
  
  Log.e("ImageCompare i2", Integer.toString(image.getWidth())+' '+Integer.toString(image.getHeight()));
  Log.e("ImageCompare i2", Integer.toString(image2.getWidth())+' '+Integer.toString(image2.getHeight()));
   
  //convert to gray scale
  image = ImageCompare.toGrayscale(image);
  image2 = ImageCompare.toGrayscale(image2);
  
    
  //convert images to pixel value array
  for(int row=0; row<=223; row++){
   for(int col=0; col<=223; col++){
    unblurredImage[row][col]=image.getPixel(row, col);
    unblurredImage2[row][col]=image2.getPixel(row, col);
   }
  }  
  
  
  //gaussian blur  
  for(int row=1; row<223; row++){
			for(int col=1; col<223; col++){
				imageArray[row][col]=( (unblurredImage[row-1][col-1])
						+((unblurredImage[row-1][col])*2)
            +(unblurredImage[row-1][col+1])
						+((unblurredImage[row][col-1])*2)
            +((unblurredImage[row][col])*4)
						+((unblurredImage[row][col+1])*2)
            +(unblurredImage[row+1][col-1])
						+((unblurredImage[row+1][col])*2)
            +(unblurredImage[row+1][col+1]))/16;
				imageArray2[row][col]=( (unblurredImage2[row-1][col-1])
						+((unblurredImage2[row-1][col])*2)
						+(unblurredImage2[row-1][col+1])
            +((unblurredImage2[row][col-1])*2)
						+((unblurredImage2[row][col])*4)
            +((unblurredImage2[row][col+1])*2)
						+(unblurredImage2[row+1][col-1])
            +((unblurredImage2[row+1][col])*2)
						+(unblurredImage2[row+1][col+1]))/16;
			}
		}
      
      
  //perform lbp calculations  
  for(int row=1; row<223; row++){
   for(int col=1; col<223; col++){
    currentPixelValue=imageArray[row][col];
    newPixelValue=0;
    if(imageArray[row-1][col-1]>currentPixelValue) newPixelValue=newPixelValue+1;
    if(imageArray[row-1][col]>currentPixelValue) newPixelValue=newPixelValue+2;
    if(imageArray[row-1][col+1]>currentPixelValue) newPixelValue=newPixelValue+4;
    if(imageArray[row][col+1]>currentPixelValue) newPixelValue=newPixelValue+8;
    if(imageArray[row+1][col+1]>currentPixelValue) newPixelValue=newPixelValue+16;
    if(imageArray[row+1][col]>currentPixelValue) newPixelValue=newPixelValue+32;
    if(imageArray[row+1][col-1]>currentPixelValue) newPixelValue=newPixelValue+64;
    if(imageArray[row][col-1]>currentPixelValue) newPixelValue=newPixelValue+128;
    lbpArray[row][col]=newPixelValue;
   }
  }
  
  for(int row=1; row<223; row++){
   for(int col=1; col<223; col++){
    currentPixelValue=imageArray2[row][col];
    newPixelValue=0;
    if(imageArray2[row-1][col-1]>currentPixelValue) newPixelValue=newPixelValue+1;
    if(imageArray2[row-1][col]>currentPixelValue) newPixelValue=newPixelValue+2;
    if(imageArray2[row-1][col+1]>currentPixelValue) newPixelValue=newPixelValue+4;
    if(imageArray2[row][col+1]>currentPixelValue) newPixelValue=newPixelValue+8;
    if(imageArray2[row+1][col+1]>currentPixelValue) newPixelValue=newPixelValue+16;
    if(imageArray2[row+1][col]>currentPixelValue) newPixelValue=newPixelValue+32;
    if(imageArray2[row+1][col-1]>currentPixelValue) newPixelValue=newPixelValue+64;
    if(imageArray2[row][col-1]>currentPixelValue) newPixelValue=newPixelValue+128;
    lbpArray2[row][col]=newPixelValue;
   }
  }
  
  
  //create histograms
  for(int row=1; row<=222; row++){
   for(int col=1; col<=222; col++){
    if(row<75 && col<75) histogram[0][imageArray[row][col]]++;
    if(row<75 && col>74 && col<149) histogram[1][imageArray[row][col]]++;
    if(row<75 && col>148 && col<223) histogram[2][imageArray[row][col]]++;
    if(row>74 && row<149 && col<75) histogram[3][imageArray[row][col]]++;
    if(row>74 && row<149 && col>75 && col<149) histogram[4][imageArray[row][col]]++;
    if(row>74 && row<149 && col>148 && col<223) histogram[5][imageArray[row][col]]++;
    if(row>148 && row<223 && col<75) histogram[6][imageArray[row][col]]++;
    if(row>148 && row<223 && col>74 && col<149) histogram[7][imageArray[row][col]]++;
    if(row>148 && row<223 && col>148 && col<223) histogram[8][imageArray[row][col]]++;
   }
  }  
  
  for(int row=1; row<=222; row++){
   for(int col=1; col<=222; col++){
    if(row<75 && col<75) histogram2[0][imageArray2[row][col]]++;
    if(row<75 && col>74 && col<149) histogram2[1][imageArray2[row][col]]++;
    if(row<75 && col>148 && col<223) histogram2[2][imageArray2[row][col]]++;
    if(row>74 && row<149 && col<75) histogram2[3][imageArray2[row][col]]++;
    if(row>74 && row<149 && col>75 && col<149) histogram2[4][imageArray2[row][col]]++;
    if(row>74 && row<149 && col>148 && col<223) histogram2[5][imageArray2[row][col]]++;
    if(row>148 && row<223 && col<75) histogram2[6][imageArray2[row][col]]++;
    if(row>148 && row<223 && col>74 && col<149) histogram2[7][imageArray2[row][col]]++;
    if(row>148 && row<223 && col>148 && col<223) histogram2[8][imageArray2[row][col]]++;
   }
  }  
  
  
  //Compare histograms (threshold = +/-10%)
  for(int k=0; k<=8; k++){
   for(int j=0; j<=255; j++){
    if(((histogram[k][j])*1.1) < histogram2[k][j] || ((histogram[k][j])*0.9) > histogram2[k][j]){
     return false;
    }
   }
  }
  
  return true;

 }
 
 public static Bitmap toGrayscale(Bitmap bmpOriginal){        
     int width, height;
     height = bmpOriginal.getHeight();
     width = bmpOriginal.getWidth();    

     Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
     Canvas c = new Canvas(bmpGrayscale);
     Paint paint = new Paint();
     ColorMatrix cm = new ColorMatrix();
     cm.setSaturation(0);
     ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
     paint.setColorFilter(f);
     c.drawBitmap(bmpOriginal, 0, 0, paint);
     return bmpGrayscale;
 }

}


