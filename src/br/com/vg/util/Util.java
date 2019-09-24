/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vg.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe contendo vários métodos utéis gerais.
 * @author Márcio Arantes e Jesimar Arantes
 */
public class Util {   

    //--------------------------MÉTODOS PÚBLICOS--------------------------------

    //================================OTHER=====================================

    /**
     * Calcula a distancia euclidiana entre os nos passados
     * @param Xi - Coordenada x do ponto inicial
     * @param Yi - Coordenada y do ponto inicial
     * @param Xf - Coordenada x do ponto final
     * @param Yf - Coordenada y do ponto final
     * @return distancia euclidiana
     */
    public static float calcDistEuclidiana(float Xi, float Yi, float Xf, float Yf){
        return (float)Math.sqrt(Math.pow((Xi - Xf), 2.0) + Math.pow((Yi - Yf), 2.0));
    }

    public static String removeExtesion(String fileName){
        char T[] = fileName.toCharArray();
        int j=T.length-1;
        while(j>=0 && T[j]!='.'){
             j--;
        }
        return fileName.substring(0, j);
    }
    
    public static String getExtesion(String fileName){
        char T[] = fileName.toCharArray();
        int j=T.length-1;
        while(j>=0 && T[j]!='.'){
             j--;
        }
        return fileName.substring(j, T.length);
    }

    public static boolean createFolder(String path){
        File f = new File(path);
        if(!f.exists()){
            return f.mkdir();
        }
        return false;
    }

    public static boolean delFolder(String path){
        return delFolder(new File(path));
    }

    public static boolean delFolder(File file){
        boolean flag = true;
        if(file.isFile()){
            flag &= file.delete();
        }else{
            for(File f : file.listFiles()){
                flag &= delFolder(f);
            }
            flag &= file.delete();
        }
        return flag;
    }

    public static boolean dirExist(String path){
        return new File(path).exists();
    }

    public static void CopyFile(String source, String destine)
            throws FileNotFoundException{
        CopyFile(new File(source), new File(destine));
    }

    public static void CopyFile(File source, File destine)
            throws FileNotFoundException{
        Formatter output = new Formatter(destine);
        Scanner  input = new Scanner(source);
        while(input.hasNextLine()){
            output.format("%s\n", input.nextLine());
        }
        input.close();
        output.close();
    }

    public static void copyFolder(String pathSource, String pathDestin)
            throws FileNotFoundException{
        copyFolder(new File(pathSource), new File(pathDestin));
    }

    public static void copyFolder(File fileSource, File fileDestin)
            throws FileNotFoundException{
        if(fileSource.isFile()){
            CopyFile(fileSource, fileDestin);
        }else{
            fileDestin.mkdirs();
            for(File f : fileSource.listFiles()){
                copyFolder(f, new File(fileDestin.getPath()+"/"+f.getName()));
            }
        }
    }

    public static void Print01(int [][]...Mnij){
        for(int M[][] : Mnij){
            System.out.println("-----------------------------");
            for(int i=0; i<M.length; i++){
                for(int j=0; j<M[i].length; j++){
                    System.out.printf("%5d", M[i][j]);
                }
                System.out.println();
            }
        }
    }

    public static void Print02(int [][]...Mnij){
        for(int M[][] : Mnij){
            System.out.println("-----------------------------");
            for(int i=0; i<M.length; i++){
                for(int j=0; j<M[i].length; j++){
                    System.out.printf("%5s", M[i][j] > 0?"+":(M[i][j] < 0?"-":"0"));
                }
                System.out.println();
            }
        }
    }

    public static LinkedList<String> Ordenar(LinkedList<String> list){
        String V[] = list.toArray(new String[list.size()]);
        for(int i=0; i<V.length; i++){
            int p = i;
            for(int j=i+1; j<V.length; j++){
                if(V[p].compareTo(V[j])>0){
                    p = j;
                }
            }
            String aux = V[p];
            V[p] = V[i];
            V[i] = aux;
        }
        list.clear();
        for(String v:V){
            list.addLast(v);
        }
        return list;
    }
}
