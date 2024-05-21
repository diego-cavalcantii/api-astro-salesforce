package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.example.Main;
import org.example.model.AstroGuia;
import org.example.repositories.AstroGuiaRepository;

import java.util.List;
import java.util.Optional;

@Path("astro")
public class AstroGuiaResource {

    AstroGuiaRepository astroGuiaRepository = new AstroGuiaRepository();

    @GET
    public Response getAstro(){
        List<AstroGuia> astroList = astroGuiaRepository.findAll();
        if (astroList.isEmpty()){
            Main.LOGGER.info("404 - Astro não encontrado");
            return Response.status(404).entity("Astro não encontrado").build();
        }
        Main.LOGGER.info("[GET] - 200 - OK");
        return Response.status(200).entity(astroList).build();
    }

    @GET
    @Path("{id}")
    public Response getAstroById(@PathParam("id") int id){
        Optional<AstroGuia> astro = astroGuiaRepository.findAstroById(id);
        if(astro.isEmpty()){
            Main.LOGGER.info("404 - Astro não encontrado");
            return Response.status(404).entity("Astro não encontrado").build();
        }
        Main.LOGGER.info("[GET] - 200 OK");
        return Response.status(200).entity(astro).build();
    }

    @POST
    public Response createAstro(AstroGuia astro){
        if (astro == null){
            Main.LOGGER.info("404 - Astro não encontrado");
            return Response.status(404).entity("Astro não encontrado").build();
        }
        astroGuiaRepository.createAstro(astro);
        Main.LOGGER.info("[POST] - 201 - Astro Creado");
        return Response.status(200).entity(astro).build();
    }

    @PUT
    @Path("{id}")
    public Response updateMonstro(@PathParam("id") int id, AstroGuia astro){
        if(astroGuiaRepository.findAstroById(id).isPresent()){
            astro.setId(id);
            astroGuiaRepository.updateAstro(astro);
            Main.LOGGER.info("[PUT] - 200 - OK");
            return Response.status(201).entity(astro).build();
        }
        Main.LOGGER.info("404 - Astro não encontrado");
        return Response.status(404).entity("Astro não encontrado").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteMonstro(@PathParam("id") int id){
        astroGuiaRepository.deleteAstro(id);
        Main.LOGGER.info("[DELETE] - 204 - Astro deletado ");
        return Response.noContent().build();
    }

}
