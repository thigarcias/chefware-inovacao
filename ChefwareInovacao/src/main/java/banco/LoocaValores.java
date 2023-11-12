package banco;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LoocaValores {
    // Memória
    private Long memoriaEmUso;
    private Long memoriaDisponivel;
    private Long memoriaTotal;
    // Disco
    private List<String> discoNome = new ArrayList<>();
    private List<String> discoModelo = new ArrayList<>();
    private Long discoTamanho;
    private Long discoLivre;
    // Rede
    private List<String> redeNome = new ArrayList<>();
    private List<String> redeIPV4 = new ArrayList<>();
    private List<String> redeIPV6 = new ArrayList<>();
    private List<Long> redeBytesRecebidos = new ArrayList<>();
    private List<Long> redeBytesEnviados = new ArrayList<>();
    private List<Long> redePacotesRecebidos = new ArrayList<>();
    private List<Long> redePacotesEnviados = new ArrayList<>();
    // CPU
    private String cpuFabricante;
    private String cpuNome;
    private Long cpuFrequencia;
    private Integer cpuFisicas;
    private Integer cpuLogicas;
    private Double cpuUso;



    public LoocaValores() {
        Looca looca = new Looca();
        // Memoria
        memoriaEmUso = looca.getMemoria().getEmUso();
        memoriaDisponivel = looca.getMemoria().getDisponivel();
        memoriaTotal = looca.getMemoria().getTotal();
        // Disco
        discoTamanho = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal();
        discoLivre = looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel();

//        List<Disco> listaDiscos = looca.getGrupoDeDiscos().getDiscos();
//        for (Disco disco: listaDiscos) {
//            discoNome.add(disco.getNome());
//            discoModelo.add(disco.getModelo());
//            discoTamanho.add(disco.getTamanho());
//        }
        // Rede

        List<RedeInterface> listaRedes = looca.getRede().getGrupoDeInterfaces().getInterfaces();
        for (int i = 0; i < listaRedes.size(); i++) {
            if(!listaRedes.get(i).getEnderecoIpv4().isEmpty()) {
                redeNome.add(listaRedes.get(i).getNome());
                redeIPV4.add(listaRedes.get(i).getEnderecoIpv4().toString());
                redeIPV6.add(listaRedes.get(i).getEnderecoIpv6().toString());
                redeBytesRecebidos.add(listaRedes.get(i).getBytesRecebidos());
                redeBytesEnviados.add(listaRedes.get(i).getBytesEnviados());
                redePacotesRecebidos.add(listaRedes.get(i).getPacotesRecebidos());
                redePacotesEnviados.add(listaRedes.get(i).getPacotesEnviados());
            }
        }

        // CPU
        cpuFabricante = looca.getProcessador().getFabricante();
        cpuNome = looca.getProcessador().getNome();
        cpuFrequencia = looca.getProcessador().getFrequencia();
        cpuFisicas = looca.getProcessador().getNumeroCpusFisicas();
        cpuLogicas = looca.getProcessador().getNumeroCpusLogicas();
        cpuUso = looca.getProcessador().getUso();
    }

    public Double converterValor (Long valor){
        Double calculoValor = valor/Math.pow(1024, 3);
        String valorFormatado = String.format("%.2f", calculoValor);
        valorFormatado = valorFormatado.replace(',', '.');
        return Double.parseDouble(valorFormatado);
    }

    public Double converterValor (Double valor){
        String valorFormatado = String.format("%.2f", valor);
        valorFormatado = valorFormatado.replace(',', '.');
        return Double.parseDouble(valorFormatado)*10;
    }

    @Override
    public String toString() {
        return """
                Memória em Uso: %d
                Memória Disponivel: %d
                Memória Total: %d
                //////////////////
                Nome dos Discos: %s
                Modelo dos Discos: %s
                Tamanho dos Discos: %s
                //////////////////
                Nome das Redes: %s
                IPV4 das Redes: %s
                IPV6 das Redes: %s
                Bytes Recebidos das Redes: %s
                Bytes Enviados das Redes: %s
                Pacotes Recebidos das Redes: %s
                Pacotes Enviados das Redes: %s""".formatted(this.memoriaEmUso, this.memoriaDisponivel,
                this.memoriaTotal, this.getDiscoNome(), this.getDiscoModelo(), this.getDiscoTamanho(),
                this.redeNome, this.redeIPV4, this.redeIPV6, this.redeBytesRecebidos, this.redeBytesEnviados, this.redePacotesRecebidos,
                this.redePacotesEnviados);
    }
}
