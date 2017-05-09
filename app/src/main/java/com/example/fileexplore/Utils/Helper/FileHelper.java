package com.example.fileexplore.Utils.Helper;

import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by 铖哥 on 2017/5/8.
 */

public class FileHelper {

    public static File[] getAllFile(String path){

        File file = new File(path);

        return file.listFiles();
    }

    public static void search(String format, File rootFile, List<String> result){

        if(rootFile.isDirectory()){

            try{
                File[] allFile = rootFile.listFiles();

                for(int i = 0 ; i < allFile.length ; i++){
                    if(allFile[i].isDirectory()){

                        search( format , allFile[i] , result);
                    }else{
                        if(allFile[i].getPath().contains(format)){
                            result.add(allFile[i].getPath());
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

}
