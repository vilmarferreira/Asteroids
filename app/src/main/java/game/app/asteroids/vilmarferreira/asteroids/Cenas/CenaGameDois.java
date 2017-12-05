package game.app.asteroids.vilmarferreira.asteroids.Cenas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGGameManager;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGInputManager;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGScene;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGScreenManager;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGSoundManager;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGSprite;
import game.app.asteroids.vilmarferreira.asteroids.AndGraph.AGTimer;
import game.app.asteroids.vilmarferreira.asteroids.R;

/**
 * Created by vilmarferreira on 03/12/2017.
 */

public class CenaGameDois extends AGScene {


    private AGSprite nave=null;
    private float cont=5;
    private List<AGSprite> vetMeteoro;
    private List<AGSprite> vetTiro;
    private List<AGSprite> vetExplao;
    private Random random= new Random();
    private AGTimer time,timeFase,timeDificuldade;
    private AGSprite[] fundo;
    private int efeito_lase, efeito_explosao;


    public CenaGameDois (AGGameManager manager)
   {
       super(manager);

   }

    public void render() {
        super.render();
        nave.render();
    }
    @Override
    public void init() {
        nave=createSprite(R.mipmap.milenium_falcon,1,1);
        nave.setScreenPercent(15,15);
        nave.vrPosition.setXY(AGScreenManager.iScreenWidth/2,nave.getSpriteHeight());

        AGSoundManager.vrMusic.loadMusic("marcha_imperial.mp3",true);
        AGSoundManager.vrMusic.play();



        this.fundo=this.fundo = new AGSprite[2];
        fundo[0] = this.createSprite(R.mipmap.fundo_dois, 1, 1);
        fundo[0].setScreenPercent(110, 110);
        fundo[0].vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);


        fundo[1] = this.createSprite(R.mipmap.fundo_dois, 1, 1);
        fundo[1].setScreenPercent(110, 110);
        fundo[1].vrPosition.setXY(AGScreenManager.iScreenWidth / 2, fundo[0].getSpriteHeight() + fundo[0].vrPosition.getY());

        //carregar efeito do tiro e explosao
        efeito_lase= AGSoundManager.vrSoundEffects.loadSoundEffect("efeito_laser.wav");
        efeito_explosao=AGSoundManager.vrSoundEffects.loadSoundEffect("efeito_explosao_um.wav");

        time= new AGTimer(1000);
        timeFase = new AGTimer(40000);
        timeDificuldade= new AGTimer(2000);

        vetMeteoro = new ArrayList<>();
        vetTiro= new ArrayList<>();
        vetExplao= new ArrayList<>();

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        //verifica game over
        for(AGSprite meteoro:vetMeteoro)
        {
            if(meteoro.collide(nave) && meteoro.bRecycled==false)
            {
                vrGameManager.setCurrentScene(4);
                return;
            }
            if(meteoro.vrPosition.getX()<0)
            {
                cont+=1;
            }
        }

        if (nave.vrPosition.getX() < (AGScreenManager.iScreenWidth - nave.getSpriteWidth() / 2) && AGInputManager.vrAccelerometer.getAccelX() > 2) {
            nave.vrPosition.setX(nave.vrPosition.getX() + 10);

        } else if (nave.vrPosition.getX() > nave.getSpriteWidth() / 2 && AGInputManager.vrAccelerometer.getAccelX() < -2) {
            nave.vrPosition.setX(nave.vrPosition.getX() - 10);


        }


        if(time.isTimeEnded())
        {
            createMeteoro();
            time.restart();
        }

        if(timeDificuldade.isTimeEnded())
        {
            cont+=1;
            timeDificuldade.restart();
        }

        if (AGInputManager.vrTouchEvents.screenClicked()) {

            createTiro();
            AGSoundManager.vrSoundEffects.play(efeito_lase);

        }

        if(timeFase.isTimeEnded())
        {
            vrGameManager.setCurrentScene(3);
            return;
        }
        //verificacoes
        verificaColissao();


        //updates
        updateTiro();
        updateMeteoro();
        time.update();
        timeFase.update();
        updateFundo();
        timeDificuldade.update();

    }


    //---------------------------

    public void createMeteoro ()
    {


        for (AGSprite meteoro : vetMeteoro) {
            if (meteoro.bRecycled) {
                meteoro.bRecycled = false;
                meteoro.bVisible=true;
                random.setSeed(System.currentTimeMillis());
                meteoro.vrPosition.setX(random.nextInt((int) (AGScreenManager.iScreenWidth - meteoro.getSpriteWidth())));
                meteoro.vrPosition.setY(AGScreenManager.iScreenHeight + meteoro.getSpriteHeight() - 10);
                return;
            }
        }

        AGSprite meteoro;
        meteoro = createSprite(R.mipmap.nave_inimiga, 1, 1);
        random.setSeed(System.currentTimeMillis());
        meteoro.setScreenPercent(20, 20);
        meteoro.fAngle = 180;
        meteoro.vrPosition.setX(random.nextInt((int) (AGScreenManager.iScreenWidth )));
        meteoro.vrPosition.setY(AGScreenManager.iScreenHeight + meteoro.getSpriteHeight() - 10);
        vetMeteoro.add(meteoro);

    }
    public void updateMeteoro()
    {
        for (AGSprite meteoro : vetMeteoro)
        {
            meteoro.vrPosition.setY(meteoro.vrPosition.getY()-cont);
            if(meteoro.vrPosition.getY()< -AGScreenManager.iScreenHeight || meteoro.vrPosition.getX()>AGScreenManager.iScreenWidth)
            {
                meteoro.bRecycled=true;
            }
        }
    }

    private void createTiro() {
        for (AGSprite tiro : vetTiro) {
            if (tiro.bRecycled) {
                tiro.bRecycled = false;
                tiro.bVisible=true;
                tiro.vrPosition.setX(nave.vrPosition.getX());
                tiro.vrPosition.setY(nave.vrPosition.getY() + nave.getSpriteHeight() / 2);

                return;
            }
        }
        AGSprite tiro;

        tiro = createSprite(R.mipmap.laser, 1, 1);
        tiro.setScreenPercent(4, 6);
        tiro.vrDirection=nave.vrDirection;
        tiro.vrPosition.setX(nave.vrPosition.getX());
        tiro.vrPosition.setY(nave.vrPosition.getY() + nave.getSpriteHeight() / 2);
        vetTiro.add(tiro);


    }
    private void updateTiro() {
        for (AGSprite tiro : vetTiro) {
            tiro.vrPosition.setY(tiro.vrPosition.getY() + 10);

            if (tiro.vrPosition.getY() > AGScreenManager.iScreenHeight)
                tiro.bRecycled = true;

        }

    }
    public void verificaColissao()
    {

        for (AGSprite tiro : vetTiro) {
            for (AGSprite meteoro : vetMeteoro)
            {
                if (tiro.collide(meteoro) && meteoro.bRecycled==false && tiro.bRecycled==false)
                {
                    tiro.bRecycled = true;
                    meteoro.bRecycled = true;
                    tiro.bVisible=false;
                    meteoro.bVisible=false;
                    AGSoundManager.vrSoundEffects.play(efeito_explosao);
                    AGSprite explosao= createExplosao(tiro.vrPosition.getX(), tiro.vrPosition.getY() + meteoro.getSpriteHeight() / 2);
                    return;
                }
            }
        }
    }
    private AGSprite createExplosao(float x, float y) {
        AGSprite explosao = createSprite(R.mipmap.explosao, 3, 2);
        explosao.addAnimation(30, false, 0, 5);
        explosao.setScreenPercent(10,10);
        explosao.fadeOut(2000);
        explosao.vrPosition.setXY(x, y);
        return explosao;
    }
    private AGSprite CreateExplosion(float x, float y)
    {
        for(AGSprite explosao: vetExplao)
        {
            if(explosao.bRecycled)
            {
                explosao.bRecycled=false;
                explosao.vrPosition.setXY(x,y);
                return explosao;

            }
        }
        AGSprite explosao;
        explosao=createSprite(R.mipmap.explosao,1,1);
        explosao.addAnimation(30,false,0,5);
        explosao.setScreenPercent(10,10);
        explosao.vrPosition.setXY(x,y);
        explosao.fadeOut(2000);
        vetExplao.add(explosao);
        return  explosao;
    }
    public void verificaGameOver()
    {
        for(AGSprite meteoro:vetMeteoro)
        {
            if(meteoro.collide(nave) && meteoro.bRecycled==false)
            {
                vrGameManager.setCurrentScene(1);
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
