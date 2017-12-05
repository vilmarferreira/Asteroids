package game.app.asteroids.vilmarferreira.asteroids.Cenas;

import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGActivityGame;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGGameManager;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGInputManager;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGScene;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGScreenManager;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGSoundManager;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGSprite;
import game.app.asteroids.vilmarferreira.asteroids.R;

/**
 * Created by vilmarferreira on 04/12/2017.
 */

public class CenaMenu extends AGScene {

    private AGSprite[] fundo;
    private AGSprite nave, btnJogar,btnSair;
    private int dir=1;
    private int count=5;

    public CenaMenu (AGGameManager manager)
    {
        super(manager);
    }
    @Override
    public void init() {

        AGSoundManager.vrMusic.loadMusic("musica_menu.mp3",true);
        AGSoundManager.vrMusic.play();

        this.fundo=this.fundo = new AGSprite[2];
        fundo[0] = this.createSprite(R.mipmap.fundo_dois, 1, 1);
        fundo[0].setScreenPercent(110, 110);
        fundo[0].vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);


        fundo[1] = this.createSprite(R.mipmap.fundo_dois, 1, 1);
        fundo[1].setScreenPercent(110, 110);
        fundo[1].vrPosition.setXY(AGScreenManager.iScreenWidth / 2, fundo[0].getSpriteHeight() + fundo[0].vrPosition.getY());


        nave=createSprite(R.mipmap.milenium_falcon,1,1);
        nave.setScreenPercent(15,15);
        nave.vrPosition.setXY(AGScreenManager.iScreenWidth/2,nave.getSpriteHeight());

        btnJogar=createSprite(R.mipmap.jogar,1,1);
        btnJogar.setScreenPercent(20,10);
        btnJogar.vrPosition.setXY(AGScreenManager.iScreenWidth/2,AGScreenManager.iScreenHeight/2);

        btnSair=createSprite(R.mipmap.btn_sair,1,1);
        btnSair.setScreenPercent(20,10);
        btnSair.vrPosition.setXY(AGScreenManager.iScreenWidth/2,btnJogar.vrPosition.getY()-(2*btnSair.getSpriteHeight()));

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        updateFundo();

        if(nave.vrPosition.getX()>AGScreenManager.iScreenWidth || nave.vrPosition.getX()<nave.getSpriteWidth())
        {
         dir*=-1;

        }
        count*=dir;
        nave.vrPosition.setX(nave.vrPosition.getX()+count);

        if(AGInputManager.vrTouchEvents.screenClicked())
        {
            if(btnJogar.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
                vrGameManager.setCurrentScene(1);
                return;
            }
            if(btnSair.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
                System.exit(0);
                return;
            }
        }



    }

    public void updateFundo() {

        this.fundo[0].vrPosition.setY(fundo[0].vrPosition.getY() - 25);
        this.fundo[1].vrPosition.setY(fundo[1].vrPosition.getY() - 25);


        if (fundo[0].vrPosition.getY() + (fundo[0].getSpriteHeight() / 2) < 0) {
            this.fundo[0].vrPosition.setY((fundo[0].getSpriteHeight() / 2) + AGScreenManager.iScreenHeight);
        }
        if (fundo[1].vrPosition.getY() + (fundo[1].getSpriteHeight() / 2) < 0) {
            this.fundo[1].vrPosition.setY((fundo[1].getSpriteHeight() / 2) + AGScreenManager.iScreenHeight);
        }
    }
}
