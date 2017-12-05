//Aplication package
package game.app.asteroids.vilmarferreira.asteroids;

//Used Packages
import android.os.Bundle;

import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGActivityGame;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGGameManager;
import game.app.asteroids.vilmarferreira.asteroids.Cenas.CenaGame;
import game.app.asteroids.vilmarferreira.asteroids.Cenas.CenaGameDois;
import game.app.asteroids.vilmarferreira.asteroids.Cenas.CenaGameOver;
import game.app.asteroids.vilmarferreira.asteroids.Cenas.CenaGameTres;
import game.app.asteroids.vilmarferreira.asteroids.Cenas.CenaMenu;
import game.app.asteroids.vilmarferreira.asteroids.Cenas.CenaVitoria;


public class Game extends AGActivityGame
{
    public void onCreate(Bundle saved)
    {
        super.onCreate(saved);

        this.vrManager= new AGGameManager(this, true); // primeiro parametro activite atual, segundo parametro uso de acelerometro

        CenaGame game = new CenaGame(vrManager);
        CenaGameOver gameOver = new CenaGameOver(vrManager);
        CenaMenu menu = new CenaMenu(vrManager);
        CenaGameDois gameFase2= new CenaGameDois(vrManager);
        CenaGameTres gameFase3=new CenaGameTres(vrManager);
        CenaVitoria cenaVitoria=new CenaVitoria(vrManager);

          vrManager.addScene(menu);//0
          vrManager.addScene(game);//1
          vrManager.addScene(gameFase2);//2
          vrManager.addScene(gameFase3);//3
          vrManager.addScene(gameOver);//4
          vrManager.addScene(cenaVitoria);//5

    }
}
