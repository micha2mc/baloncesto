public class calcularVotosImpl implements VotosService {
    private DataService dataService;
    @Override
    public int sumarVotos(String nombre) {
        return dataService.getListOfVotos(nombre)+1;
    }

    public void setDataService(DataService dataService) {

        this.dataService = dataService;

    }
}
