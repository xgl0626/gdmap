package com.example.gdmap.utils

import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
import android.media.MediaPlayer
import android.view.Surface
import android.view.TextureView

object MediaPlayUtils:TextureView.SurfaceTextureListener,MediaPlayer.OnErrorListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener{
    var url:String? =null
    private var textureView:TextureView?=null
    var mediaPlayer:MediaPlayer?=null
    var surface: Surface?=null
    fun setInit(url:String,textureView: TextureView,mediaplayer: MediaPlayer?)
    {
        this.url=url
        this.textureView=textureView
        this.mediaPlayer= mediaPlayer
    }
    override fun onSurfaceTextureSizeChanged(surfaceTexture: SurfaceTexture, p1: Int, p2: Int) {
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
        return true
    }

    override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, p1: Int, p2: Int) {
         surface= Surface(surfaceTexture)
    }
    fun initClick()
    {
        textureView?.surfaceTextureListener=this
        mediaPlayer?.setOnErrorListener(this)
        mediaPlayer?.setOnCompletionListener(this)
        mediaPlayer?.setOnPreparedListener(this)
        mediaPlayer?.setScreenOnWhilePlaying(true)
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        PlayVideoThread().run()
    }

    override fun onError(mediaPlayer: MediaPlayer?, p1: Int, p2: Int): Boolean {
        restart()
        return true
    }
    fun restart()
    {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.setDataSource(url)
        mediaPlayer?.start()
    }
    override fun onPrepared(mediaplayer: MediaPlayer?) {
        mediaPlayer?.setSurface(surface)
        mediaplayer?.start()
    }
    fun start()
    {
        mediaPlayer?.start()
    }
    fun stop()
    {
        mediaPlayer?.stop()
    }
    fun getDuration(): Int {
       return mediaPlayer!!.duration
    }
    fun getCurrentDuration():Int
    {
        return mediaPlayer!!.currentPosition
    }
    fun setVolume(volume:Float)
    {
        mediaPlayer?.setVolume(volume,volume);
    }
    fun seekTo(time:Int)
    {
        mediaPlayer?.seekTo(time)
    }
    fun pause()
    {
        mediaPlayer?.pause()
    }
    override fun onCompletion(mediaplayer: MediaPlayer?) {
        mediaPlayer?.start()
    }
     class PlayVideoThread :Thread(){
        override fun run() {
            super.run()
            try {
                mediaPlayer?.setDataSource(url)
                mediaPlayer?.setVideoScalingMode(VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                mediaPlayer?.prepareAsync();
            }catch (e:Exception)
            {
                e.printStackTrace()
            }

        }
    }

}