/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UsuarioDAO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sistemaempleados.conexionba.ConexionMongo;
import com.sistemaempleados.modelo.Usuario;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author BryanAlfaro
 */
public class UsuarioDAO {
    private MongoCollection<Document> coleccion;
    public UsuarioDAO() {
        coleccion = ConexionMongo.getBaseDatos().getCollection("usuarios");
    }
    
    public void insertar(Usuario usuario) {

        Document documento = new Document()
                .append("usuario", usuario.getUsuario())
                .append("nombre", usuario.getNombre())
                .append("passwordHash", usuario.getPasswordHash())
                .append("activo", usuario.isActivo());

        coleccion.insertOne(documento);
    }
    
    public Usuario buscarPorUsuario(String usuario) {

        Document documento = coleccion.find(
                Filters.eq("usuario", usuario)).first();

        if (documento == null) {
            return null;
        }

        return new Usuario(
                documento.getString("usuario"),
                documento.getString("nombre"),
                documento.getString("passwordHash"),
                documento.getBoolean("activo", true));
    }
    
    public boolean hayUsuarios() {
        return coleccion.countDocuments() > 0;
    }

    
    // Actualizar un usuario
    public void actualizar(Usuario usuario) {

        Document documento = new Document()
                .append("usuario", usuario.getUsuario())
                .append("nombre", usuario.getNombre())
                .append("passwordHash", usuario.getPasswordHash())
                .append("activo", usuario.isActivo());

        coleccion.replaceOne(Filters.eq("usuario", usuario.getUsuario()), documento);
    }

    // Obtener todos los usuarios
    public List<Usuario> listarTodos() {

        List<Usuario> lista = new ArrayList<>();

        for (Document documento : coleccion.find()) {
            lista.add(new Usuario(
                    documento.getString("usuario"),
                    documento.getString("nombre"),
                    documento.getString("passwordHash"),
                    documento.getBoolean("activo", true)));
        }

        return lista;
    }
}
