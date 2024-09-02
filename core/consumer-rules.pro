##---------------Begin: proguard configuration for SQLCipher  ----------
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }


##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}


##---------------Begin: proguard configuration for Retrofit  ----------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

-dontwarn kotlinx.**


##---------------Begin: proguard configuration for Glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# Uncomment for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


##---------------Begin: proguard configuration for RxJava  ----------
# Uncomment if you use RxJava
#-dontwarn java.util.concurrent.Flow*

-dontwarn com.example.core.data.Resource$Error
-dontwarn com.example.core.data.Resource$Loading
-dontwarn com.example.core.data.Resource$Success
-dontwarn com.example.core.data.Resource
-dontwarn com.example.core.data.source.remote.response.Cast
-dontwarn com.example.core.data.source.remote.response.Credits
-dontwarn com.example.core.data.source.remote.response.Genre
-dontwarn com.example.core.data.source.remote.response.MovieDetailResponse
-dontwarn com.example.core.databinding.ViewEmptyBinding
-dontwarn com.example.core.databinding.ViewSearchboxBinding
-dontwarn com.example.core.di.CoreModuleKt
-dontwarn com.example.core.domain.model.Movie
-dontwarn com.example.core.domain.repository.IMovieRepository
-dontwarn com.example.core.domain.usecase.MovieInteractor
-dontwarn com.example.core.domain.usecase.MovieUseCase
-dontwarn com.example.core.ui.ActorModel_
-dontwarn com.example.core.ui.EpoxyCallbacks
-dontwarn com.example.core.ui.EqualSpaceGridItemDecoration
-dontwarn com.example.core.ui.HeaderModelBuilder
-dontwarn com.example.core.ui.HeaderModel_
-dontwarn com.example.core.ui.InfoTextModelBuilder
-dontwarn com.example.core.ui.InfoTextModel_
-dontwarn com.example.core.ui.KotlinEpoxyHolder
-dontwarn com.example.core.ui.LoadingModelBuilder
-dontwarn com.example.core.ui.LoadingModel_
-dontwarn com.example.core.ui.MovieModel$MovieViewHolder
-dontwarn com.example.core.ui.MovieModelBuilder
-dontwarn com.example.core.ui.MovieModel_
-dontwarn com.example.core.ui.MovieSearchResultModel$MovieSearchResultHolder
-dontwarn com.example.core.ui.MovieSearchResultModelBuilder
-dontwarn com.example.core.ui.MovieSearchResultModel_
-dontwarn com.example.core.utils.DataMapper
-dontwarn com.example.core.utils.SingleLiveEvent
-dontwarn com.example.core.utils.UtilKt

-dontwarn org.bouncycastle.jsse.**
-dontwarn org.conscrypt.*
-dontwarn org.openjsse.**

# Retrofit and OkHttp
-keep class retrofit2.** { *; }
-keep class retrofit2.adapter.** { *; }
-keep class retrofit2.converter.** { *; }
-keep class okhttp3.** { *; }
-keep class okio.** { *; }

# Keep Kotlin classes and metadata
-keep class kotlin.** { *; }
-keepclassmembers class kotlin.Metadata { *; }

-keep class * extends com.airbnb.epoxy.EpoxyController { *; }
-keep class * extends com.airbnb.epoxy.ControllerHelper { *; }
-keepclasseswithmembernames class * { @com.airbnb.epoxy.AutoModel <fields>; }

# Keep all Koin core classes
-keep class org.koin.** { *; }

# Keep classes annotated with @Module and @Provides
-keep @org.koin.core.annotation.* class * { *; }

# Keep specific Koin methods used for injection
-keepclassmembers class * {
    public <init>(...);
    public void inject(...);
    public kotlin.Lazy inject(...);
#    public kotlin.Lazy org.koin.core.component.KoinComponent.get(...);
}

# Keep classes that are injected by Koin
-keepclassmembers class * {
    @org.koin.core.annotation.* *;
}

# Keep all enum classes
-keep class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep all classes in the core module, but allow obfuscation
-keep, allowobfuscation class com.example.core.di.CoreModuleKt { *; }

-keepclasseswithmembernames class com.example.core.di.CoreModuleKt { *;}

-keepnames,allowobfuscation interface com.example.core.ui.EpoxyCallbacks { *;}


