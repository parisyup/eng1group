package net.shipsandgiggles.pirate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import net.shipsandgiggles.pirate.pref.GamePreferences;

public class SoundController{

        /** sound controller to control all the sound effeccts and music*/

        public Music music;
        public Sound buttonPress;
        public Sound Explosion;
        public Sound cannonShot;
        public Music seaNoises;
        public float volume = 0;
        public float musicVolume = 0;

        public SoundController(){

                /** construction of the controller*/

                /** returns if already set*/
                if(music != null){if(music.isPlaying())return;}
                if(seaNoises!= null) if(seaNoises.isPlaying())return;



                /** setting the music and sound effects*/
                music = Gdx.audio.newMusic(Gdx.files.internal("models/music.mp3"));
                seaNoises = Gdx.audio.newMusic(Gdx.files.internal("models/seaSounds.mp3"));
                cannonShot = Gdx.audio.newSound(Gdx.files.internal("models/cannonShot.mp3"));
                buttonPress = Gdx.audio.newSound(Gdx.files.internal("models/buttonPress.mp3"));
                Explosion = Gdx.audio.newSound(Gdx.files.internal("models/explosion.mp3"));

                /** checks according to preferences and updates based on them*/
                if(GamePreferences.get().isMusicEnabled() && !music.isPlaying()){
                        music.setVolume(GamePreferences.get().getVolumeLevel());
                        music.setLooping(true);
                }
                if(GamePreferences.get().isVolumeEnabled() && !seaNoises.isPlaying()){
                        seaNoises.setVolume(GamePreferences.get().getVolumeLevel());
                        seaNoises.setLooping(true);
                }

        }
        public void update(){

                /** update for all preferences*/
                if(!GamePreferences.get().isMusicEnabled()) music.stop();
                if(!GamePreferences.get().isVolumeEnabled()) seaNoises.stop();
                if(GamePreferences.get().isVolumeEnabled() && !music.isPlaying()){
                        music.play();
                }
                if(GamePreferences.get().isVolumeEnabled() && !seaNoises.isPlaying()){
                        seaNoises.play();
                }
                if(musicVolume != music.getVolume()) {
                        music.setVolume(GamePreferences.get().getVolumeLevel());
                        return;
                }

                if(volume != seaNoises.getVolume()) {
                        seaNoises.setVolume(GamePreferences.get().getVolumeLevel());
                        return;
                }

        }

        public void playExplosion(){
                /** play explosion noise*/
                if(!GamePreferences.get().isVolumeEnabled()) return;
                long id = Explosion.play(GamePreferences.get().getVolumeLevel());
                Explosion.setPitch(id, 2);
                Explosion.setLooping(id,false);
        }
        public void playButtonPress(){
                /** play button press noise*/
                if(!GamePreferences.get().isVolumeEnabled()) return;
                long id = buttonPress.play(GamePreferences.get().getVolumeLevel());
                buttonPress.setPitch(id, 2);
                buttonPress.setLooping(id,false);
        }
        public void playCannonShot(){
                /** play cannon shot noise*/
                if(!GamePreferences.get().isVolumeEnabled()) return;
                long id = cannonShot.play(GamePreferences.get().getVolumeLevel());
                cannonShot.setPitch(id, 2);
                cannonShot.setLooping(id,false);
        }

        public void pauseAll() {
                /** pauses all noises */
                music.pause();
                seaNoises.pause();
                music.dispose();
                seaNoises.dispose();
        }
}
