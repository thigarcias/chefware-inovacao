package looca;

import com.github.britooo.looca.api.core.Looca;

public class Operacao {
    Looca looca = new Looca();
    public void mostrarMemoria(){
        System.out.println("--- USO DA MEMORIA ---");
        System.out.println("Total de Memória:\n"+looca.getMemoria().getTotal().toString()+"\n"+"Memória em Uso\n"+looca.getMemoria().getEmUso().toString()
                +"\n"+"Memória Disponivel\n"+looca.getMemoria().getDisponivel().toString()+"\n");
    }
    public void mostrarProcessos(){
        System.out.println("--- PROCESSOS ---");
        System.out.println("Processos:\n"+looca.getGrupoDeProcessos().getProcessos().toString()+"\n"+"Total de Processos\n"+looca.getGrupoDeProcessos().getTotalProcessos()
                +"\n"+"Total de Threads\n"+looca.getGrupoDeProcessos().getTotalThreads()+"\n");
    }
    public void mostrarCpu(){
        System.out.println("--- INFORMAÇÕES GERAIS DA CPU ----");
        System.out.println(looca.getProcessador().toString());
    }
    public void mostrarDisco(){
        System.out.println("--- INFORMAÇÕES GERAIS DO DISCO ---");
        System.out.println("Discos:\n"+looca.getGrupoDeDiscos().getDiscos().toString()+"\n"+"Quantidade de Discos\n"+looca.getGrupoDeDiscos().getQuantidadeDeDiscos().toString()
                +"\n"+"Quantidade de Volumes\n"+looca.getGrupoDeDiscos().getQuantidadeDeVolumes().toString()+"\n"+
                "Tamanho total\n"+looca.getGrupoDeDiscos().getTamanhoTotal().toString()+"\n"+
                "Volumes\n"+looca.getGrupoDeDiscos().getVolumes().toString());
    }
    public void mostrarRede(){
        System.out.println("--- REDE ---");
        System.out.println("Grupo de Interface:\n"+ looca.getRede().getGrupoDeInterfaces().getInterfaces().toString()+"\nParametros:\n"+looca.getRede().getParametros());
    }


}
