package fes.aragon.unam.administracion;

import fes.aragon.unam.administracion.model.Trabajador;

import java.io.*;
import java.util.ArrayList;

public class ArchivoTrabajador {

    public static void escribir(ArrayList<Trabajador> trabajadores, String archivo) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/datos/"+ archivo);
        file.getParentFile().mkdirs();

        FileOutputStream out =new FileOutputStream(file);
        ObjectOutputStream salida = new ObjectOutputStream(out);
        salida.writeObject(trabajadores);
        salida.close();
        out.close();
    }
    public static ArrayList<Trabajador> leer(String archivo) throws IOException, ClassNotFoundException {
        File file = new File(System.getProperty("user.dir") + "/datos/" + archivo);
        FileInputStream in = new FileInputStream(file);
        ObjectInputStream entrada = new ObjectInputStream(in);
        ArrayList<Trabajador> datos = (ArrayList<Trabajador>) entrada.readObject();
        entrada.close();
        in.close();
        return datos;
    }
}
