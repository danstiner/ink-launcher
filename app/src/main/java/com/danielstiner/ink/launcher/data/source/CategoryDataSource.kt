package com.danielstiner.ink.launcher.data.source

import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.os.Build
import com.danielstiner.ink.launcher.data.model.AppCategory

class CategoryDataSource {

    fun categorize(info: ResolveInfo) =
        categorize(
            info.activityInfo.packageName,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) info.activityInfo.applicationInfo.category else null
        )

    fun categorize(packageName: String, applicationInfoCategory: Int? = null) =
        when {
            packageName in packageToCategory -> packageToCategory[packageName]!!
            applicationInfoCategory in categoryToCategory -> categoryToCategory[applicationInfoCategory]!!
            else -> AppCategory.UNDEFINED
        }

    companion object {
        val categoryToCategory = mapOf(
            ApplicationInfo.CATEGORY_ACCESSIBILITY to AppCategory.ACCESSIBILITY,
            ApplicationInfo.CATEGORY_AUDIO to AppCategory.LISTEN,
            ApplicationInfo.CATEGORY_GAME to AppCategory.PLAY,
            ApplicationInfo.CATEGORY_IMAGE to AppCategory.CAPTURE,
            ApplicationInfo.CATEGORY_MAPS to AppCategory.EXPLORE,
            ApplicationInfo.CATEGORY_NEWS to AppCategory.NEWS,
            ApplicationInfo.CATEGORY_PRODUCTIVITY to AppCategory.UTILITY,
            ApplicationInfo.CATEGORY_SOCIAL to AppCategory.SOCIAL,
            ApplicationInfo.CATEGORY_UNDEFINED to AppCategory.UNDEFINED,
            ApplicationInfo.CATEGORY_VIDEO to AppCategory.WATCH,
        )
        val packageToCategory = mapOf(
            "com.synology.DSaudio" to AppCategory.LISTEN,
            "com.synology.DScam" to AppCategory.WATCH,
            "com.synology.dsmail" to AppCategory.MESSAGE,
            "com.synology.projectkailash" to AppCategory.CAPTURE,
            "com.synology.moments" to AppCategory.CAPTURE,
            "com.synology.dsnote" to AppCategory.CAPTURE,
            "com.synology.dsvideo" to AppCategory.WATCH,
            "com.synology.dschat" to AppCategory.MESSAGE,
            "com.synology.dsphoto" to AppCategory.CAPTURE,
            "com.enki.insights" to AppCategory.LEARN,
            "com.pluralsight" to AppCategory.LEARN,
            "org.edx.mobile" to AppCategory.LEARN,
            "com.udemy.android" to AppCategory.LEARN,
            "com.skillshare.Skillshare" to AppCategory.LEARN,
            "org.coursera.android" to AppCategory.LEARN,
            "com.spotlightsix.zentimerlite2" to AppCategory.HEALTH,
            "com.fitness22.meditation" to AppCategory.HEALTH,
            "com.getsomeheadspace.android" to AppCategory.HEALTH,
            "com.meditation.elevenminute" to AppCategory.HEALTH,
            "com.changecollective.tenpercenthappier" to AppCategory.HEALTH,
            "com.datechnologies.tappingsolution" to AppCategory.HEALTH,
            "com.capitalx.blissfully" to AppCategory.HEALTH,
            "com.elevatelabs.geonosis" to AppCategory.HEALTH,
            "org.wakingup.android" to AppCategory.HEALTH,
            "com.aa.android" to AppCategory.FLY,
            "cz.absolutno.sifry" to AppCategory.PLAY,
            "com.ViktorDikov.BetrayalCharacterStats" to AppCategory.PLAY,
            "cgeo.geocaching" to AppCategory.EXPLORE,
            "ch.protonmail.android" to AppCategory.MESSAGE,
            "ch.threema.app" to AppCategory.MESSAGE,
            "co.hinge.app" to AppCategory.DATE,
            "com.Funimation.FunimationNow" to AppCategory.WATCH,
            "com.adsk.sketchbook" to AppCategory.CREATE,
            "com.airbnb.android" to AppCategory.STAY,
            "com.alaskaairlines.android" to AppCategory.FLY,
            "com.alltrails.alltrails" to AppCategory.HIKE,
            "com.amazon.kindle" to AppCategory.READ,
            "com.android.camera.CameraLauncher" to AppCategory.CAPTURE,
            "com.android.chrome" to AppCategory.BROWSE,
            "com.android.settings" to AppCategory.UTILITY,
            "com.anovaculinary.android" to AppCategory.COOK,
            "com.audible.application" to AppCategory.LISTEN,
            "com.axs.android" to AppCategory.PLAN,
            "com.azure.authenticator" to AppCategory.AUTHENTICATE,
            "com.betterment" to AppCategory.BANK,
            "com.blendle.app" to AppCategory.NEWS,
            "com.blizzard.bma" to AppCategory.AUTHENTICATE,
            "com.bumble.app" to AppCategory.DATE,
            "com.cbs.app" to AppCategory.WATCH,
            "com.coffeemeetsbagel" to AppCategory.DATE,
            "com.danielstiner.glimdroid" to AppCategory.WATCH,
            "tv.glimesh.app" to AppCategory.WATCH,
            "com.danielstiner.ink.launcher" to AppCategory.HIDE,
            "com.delta.mobile.android" to AppCategory.FLY,
            "com.discord" to AppCategory.MESSAGE,
            "com.disney.disneyplus" to AppCategory.WATCH,
            "com.duolingo" to AppCategory.LEARN,
            "com.ellation.vrv" to AppCategory.WATCH,
            "com.espn.score_center" to AppCategory.WATCH,
            "com.eventbrite.attendee" to AppCategory.PLAN,
            "com.evilduck.musiciankit" to AppCategory.LISTEN,
            "com.facebook.mlite" to AppCategory.MESSAGE,
            "com.facebook.orca" to AppCategory.MESSAGE,
            "com.fastmail.app" to AppCategory.MESSAGE,
            "com.gg.guilded" to AppCategory.MESSAGE,
            "com.goodreads" to AppCategory.SOCIAL,
            "com.google.android.apps.authenticator2" to AppCategory.AUTHENTICATE,
            "com.google.android.apps.chromecast.app" to AppCategory.UTILITY,
            "com.google.android.apps.fitness" to AppCategory.WORKOUT,
            "com.google.android.apps.gmoney" to AppCategory.BANK,
            "com.google.android.apps.meetings" to AppCategory.MEET,
            "com.google.android.apps.messaging" to AppCategory.MESSAGE,
            "com.google.android.apps.translate" to AppCategory.UTILITY,
            "com.google.android.calendar" to AppCategory.PLAN,
            "com.google.android.contacts" to AppCategory.CALL,
            "com.google.android.dialer" to AppCategory.CALL,
            "com.google.android.gm" to AppCategory.MESSAGE,
            "com.google.android.gm.lite" to AppCategory.MESSAGE,
            "com.google.android.keep" to AppCategory.NOTE,
            "com.google.android.talk" to AppCategory.MESSAGE,
            "com.google.android.youtube" to AppCategory.WATCH,
            "com.groundspeak.geocaching.intro" to AppCategory.OUTSIDE,
            "com.groupme.android" to AppCategory.MESSAGE,
            "com.habitrpg.android.habitica" to AppCategory.PLAN,
            "com.hbo.hbonow" to AppCategory.WATCH,
            "com.hulu.plus" to AppCategory.WATCH,
            "com.ichi2.anki" to AppCategory.LEARN,
            "com.jms.coloring.club" to AppCategory.CREATE,
            "com.mcy.cihan.darkskyxweather" to AppCategory.OUTSIDE,
            "com.microsoft.teams" to AppCategory.MESSAGE,
            "com.mint" to AppCategory.BANK,
            "com.netflix.mediaclient" to AppCategory.WATCH,
            "com.pandora.android" to AppCategory.LISTEN,
            "com.plexapp.android" to AppCategory.WATCH,
            "com.reddit.frontpage" to AppCategory.SOCIAL,
            "com.spotify.music" to AppCategory.LISTEN,
            "com.squareup.cash" to AppCategory.BANK,
            "com.stephaneginier.nomad" to AppCategory.CREATE,
            "com.strava" to AppCategory.WORKOUT,
            "com.twitter.android" to AppCategory.SOCIAL,
            "com.venmo" to AppCategory.BANK,
            "com.verizon.messaging.vzmsgs" to AppCategory.MESSAGE,
            "com.wealthfront" to AppCategory.BANK,
            "com.whatsapp" to AppCategory.MESSAGE,
            "com.whatsapp.w4b" to AppCategory.MESSAGE,
            "com.workSPACE.Fraksl" to AppCategory.CREATE,
            "com.zhiliaoapp.musically" to AppCategory.SOCIAL,
            "im.vector.app" to AppCategory.MESSAGE,
            "keepass2android.keepass2android" to AppCategory.UTILITY,
            "lv.crochet.app" to AppCategory.CREATE,
            "me.lyft.android" to AppCategory.EXPLORE,
            "me.tomhicks.asketchaday" to AppCategory.CREATE,
            "org.briarproject.briar.android" to AppCategory.MESSAGE,
            "org.inaturalist.seek" to AppCategory.UTILITY,
            "org.telegram.messenger" to AppCategory.MESSAGE,
            "org.telegram.plus" to AppCategory.MESSAGE,
            "org.thoughtcrime.securesms" to AppCategory.MESSAGE,
            "org.videolan.vlc" to AppCategory.WATCH,
            "tv.twitch.android.app" to AppCategory.WATCH,
            "us.zoom.videomeetings" to AppCategory.MEET,
        )
    }
}