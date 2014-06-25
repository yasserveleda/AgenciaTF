/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 12205008
 */
public class App {
    public static void main(String[] args)
    {
        Simulacao sim = new Simulacao(true);
        sim.simular();
        sim.imprimirResultados();
        
        InterfaceGrafica in = new InterfaceGrafica();
        
    }
}


