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
 * Created by vilmarferreira on 04/12/2017.
 */

public class CenaGameTres extends AGScene {

    private AGSprite nave=null, estrela_morte;
    private float cont=5;
    private List<AGSprite> vetMeteoro;
    private List<AGSprite> vetTiro,vetTiroEstrela;
    private List<AGSprite> vetExplao;
    private Random random= new Random();
    private AGTimer time, timeTiroEstrela,timeDificuldade;
    private AGSprite[] fundo;
    private int efeito_lase, efeito_explosao;
    private int acertoEstrela=0;
    private int mov=10,dir=1;

    public  CenaGameTres(AGGameManager manager)
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
        fundo[0] = this.createSprite(R.mipmap.fundo_treis, 1, 1);
        fundo[0].setScreenPercent(110, 110);
        fundo[0].vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);


        fundo[1] = this.createSprite(R.mipmap.fundo_treis, 1, 1);
        fundo[1].setScreenPercent(110, 110);
        fundo[1].vrPosition.setXY(AGScreenManager.iScreenWidth / 2, fundo[0].getSpriteHeight() + fundo[0].vrPosition.getY());

        //carregar efeito do tiro e explosao
        efeito_lase= AGSoundManager.vrSoundEffects.loadSoundEffect("efeito_laser.wav");
        efeito_explosao=AGSoundManager.vrSoundEffects.loadSoundEffect("efeito_explosao_um.wav");

        time= new AGTimer(3000);
        timeTiroEstrela = new AGTimer(1000);
        timeDificuldade= new AGTimer(2000);

        vetMeteoro = new ArrayList<>();
        vetTiro= new ArrayList<>();
        vetExplao= new ArrayList<>();
        vetTiroEstrela= new ArrayList<>();

        estrela_morte= createSprite(R.mipmap.estrela_da_morte,1,1);
        estrela_morte.setScreenPercent(30,30);
        estrela_morte.vrPosition.setXY(AGScreenManager.iScreenWidth/2,AGScreenManager.iScreenHeight-estrela_morte.getSpriteHeight());

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

//        //verifica game over
        for(AGSprite meteoro:vetMeteoro)
        {
            if(meteoro.collide(nave) && meteoro.bRecycled==false)
            {
                vrGameManager.setCurrentScene(4);
                return;
            }
        }

        for(AGSprite tiro:vetTiroEstrela)
        {
            if(tiro.collide(nave) && tiro.bRecycled==false)
            {
                vrGameManager.setCurrentScene(4);
                return;
            }
        }

        if(acertoEstrela==10)
        {
            vrGameManager.setCurrentScene(5);
            return;
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
        if(timeTiroEstrela.isTimeEnded())
        {
            createTiroEstrela();
            timeTiroEstrela.restart();
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

        //verificacoes
        verificaColissao();

        acertaNave();
        //updates
        updateTiro();
        updateMeteoro();
        updateTiroEstrela();

        time.update();
        timeTiroEstrela.update();

        timeDificuldade.update();
        MoveEstrela();

    }


    //---------------------------------------------------------

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

    //---------------TIRO ESTRELA------------------------
    private void createTiroEstrela() {
        for (AGSprite tiro : vetTiroEstrela) {
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
        tiro.vrPosition.setX(estrela_morte.vrPosition.getX());
        tiro.vrPosition.setY(estrela_morte.vrPosition.getY() + estrela_morte.getSpriteHeight() / 2);
        vetTiroEstrela.add(tiro);


    }
    private void updateTiroEstrela() {
        for (AGSprite tiro : vetTiroEstrela) {
            tiro.vrPosition.setY(tiro.vrPosition.getY() - 10);

            if (tiro.vrPosition.getY() > AGScreenManager.iScreenHeight)
                tiro.bRecycled = true;

        }

    }
    //------------------MOVE NAVE----------------------------------
    public void MoveEstrela()
    {
        if(estrela_morte.vrPosition.getX()>AGScreenManager.iScreenWidth || estrela_morte.vrPosition.getX()< estrela_morte.getSpriteWidth())
        {
            dir*=-1;
        }
        mov*=dir;
        estrela_morte.vrPosition.setX(estrela_morte.vrPosition.getX()+mov);
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
                   // AGSprite explosao= createExplosao(tiro.vrPosition.getX(), tiro.vrPosition.getY() + meteoro.getSpriteHeight() / 2);
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

    public void acertaNave()
    {
        for(AGSprite tiro: vetTiro)
        {
            if (tiro.collide(estrela_morte) && estrela_morte.bRecycled==false && tiro.bRecycled==false)
            {
                tiro.bRecycled = true;
                tiro.bVisible=false;

                AGSoundManager.vrSoundEffects.play(efeito_explosao);
                // AGSprite explosao= createExplosao(tiro.vrPosition.getX(), tiro.vrPosition.getY() + meteoro.getSpriteHeight() / 2);
                acertoEstrela++;

                return;
            }
        }
    }



}
