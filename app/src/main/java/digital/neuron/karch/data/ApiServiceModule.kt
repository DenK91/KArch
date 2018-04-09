package digital.neuron.karch.data

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import digital.neuron.karch.data.api.HeaderInterceptor
import digital.neuron.karch.data.api.QuestionService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
class ApiServiceModule {

    @Provides
    @Named(BASE_URL)
    internal fun provideBaseUrl(): String {
        return Config.API_HOST
    }

    @Provides
    @Singleton
    internal fun provideHeaderInterceptor(): HeaderInterceptor {
        return HeaderInterceptor()
    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    @Provides
    @Singleton
    internal fun provideHttpClient(headerInterceptor: HeaderInterceptor,
                                   httpInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(headerInterceptor)
                .addInterceptor(httpInterceptor)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    internal fun provideRxJavaAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(@Named(BASE_URL) baseUrl: String, converterFactory: Converter.Factory,
                                 callAdapterFactory: CallAdapter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(client)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideQuestionService(retrofit: Retrofit): QuestionService {
        return retrofit.create(QuestionService::class.java)
    }

    companion object {
        private const val BASE_URL = "base_url"
    }
}
