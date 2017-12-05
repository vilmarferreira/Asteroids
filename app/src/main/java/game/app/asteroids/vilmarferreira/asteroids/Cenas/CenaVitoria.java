package game.app.asteroids.vilmarferreira.asteroids.Cenas;

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

public class CenaVitoria extends AGScene {
    AGSprite vitoria,btnMenu,btnJogar;;
    public CenaVitoria(AGGameManager manager)
    {
        super(manager);
    }
    @Override
    public void init() {
        vitoria=createSprite(R.mipmap.win,1,1);
        vitoria.setScreenPercent(100,100);
        vitoria.vrPosition.setXY(AGScreenManager.iScreenWidth/2,AGScreenManager.iScreenHeight/2);

        btnJogar=createSprite(R.mipmap.jogar,1,1);
        btnJogar.setScreenPercent(20,10);
        btnJogar.vrPosition.setXY(AGScreenManager.iScreenWidth/2,AGScreenManager.iScreenHeight/2);

        btnMenu=createSprite(R.mipmap.btn_menu,1,1);
        btnMenu.setScreenPercent(20,10);
        btnMenu.vrPosition.setXY(AGScreenManager.iScreenWidth/2,btnJogar.vrPosition.getY()-(2*btnMenu.getSpriteHeight()));

        AGSoundManager.vrMusic.loadMusic("tema_vitoria.mp3",true);
        AGSoundManager.vrMusic.play();

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        if(AGInputManager.vrTouchEvents.screenClicked())
        {
            if(btnJogar.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
                vrGameManager.setCurrentScene(1);
                return;
            }
            if(btnMenu.collide(AGInputManager.vrTouchEvents.getLastPosition()))
            {
                vrGameManager.setCurrentScene(0);

                return;
            }
        }

    }
}
