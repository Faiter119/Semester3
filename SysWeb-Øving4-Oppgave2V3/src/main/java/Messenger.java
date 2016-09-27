import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/messages/")
public class Messenger {

    private static List<String> messages = new ArrayList<String>() {{
        add("This is a message from the rest server");
    }};

    /*@GET
    @Path("/{}")
    @Produces(MediaType.APPLICATION_JSON)
    public Kunde getKunde(@PathParam("kundeId") String kundeId) {

        if (!kunder.containsKey(kundeId)) throw new NotFoundException(kundeId+ " not found");

        return kunder.get(kundeId);
    }*/

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<String> getMessages() {
        return messages;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addMessage(String message){
        messages.add(message);
    }

    /*@DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{kundeId}")
    public void deleteKunde(String id){ kunder.remove(id);}*/

    /*@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateKunde(Kunde newKunde){

        if (!kunder.containsKey(newKunde.getId())) throw new NotFoundException(newKunde.getId()+" not found");

        kunder.put(newKunde.getId(), newKunde);
    }*/
}