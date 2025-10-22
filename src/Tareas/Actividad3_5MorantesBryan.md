# ACTIVIDAD 3.5 — Intents en Service y Broadcast

## 1. Intents en el contexto de un Service  

### 1.1 Uso y opciones  
Según la documentación oficial de Android Developers:  
- Para iniciar un servicio se utiliza un `Intent` pasado a `startService()` o `bindService()` (o en versiones modernas: `Context.startForegroundService()` para servicios en primer plano).  
- Es **recomendado** usar **intents explícitos** cuando se inicia un servicio. Android advierte: “always use an explicit intent when starting a Service” porque usar un intent implícito puede provocar riesgos de seguridad.  
- En el manifest, se declara el `<service>` con atributos como `android:exported`, etc.  
- Cuando el servicio es “started” (iniciado mediante `startService`), su ciclo de vida es independiente del componente que lo llamó; debe detenerse él mismo con `stopSelf()` o que otro componente llame `stopService()`.  
- Cuando el servicio es “bound” (vinculado) mediante `bindService()`, el intent también se usa para pasar datos al servicio, y el cliente se conecta al servicio.  
- Se evita usar `Intent` implícitos con servicios: “Beginning with Android 5.0 (API level 21), the system throws an exception if you call bindService() with an implicit intent.”  

### 1.2 Opciones comunes del Intent para Service  
- **Action**: usar `intent.setAction(...)` para indicar una acción concreta.  
- **Component / Clase del servicio**: especificar la clase del servicio mediante `new Intent(context, MyService.class)` o `intent.setClassName(...)`.  
- **Extras**: `intent.putExtra(...)` para pasar datos.  
- **Flags**: `intent.setFlags(...)` (no muy común en servicios).  
- **Data / Uri**: `intent.setData(...)` si se necesita.  

### 1.3 Ejemplos en Java  

#### Ejemplo A: Servicio iniciado (Started Service)
```java
// MyService.java
public class MyService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String param = intent.getStringExtra("param_key");
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
```
```java
// Desde una Activity
Intent intent = new Intent(this, MyService.class);
intent.putExtra("param_key", "valorEjemplo");
startService(intent);
```

#### Ejemplo B: Servicio vinculado (Bound Service)
```java
public class MyBoundService extends Service {
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        MyBoundService getService() {
            return MyBoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public int getRandomNumber() {
        return new Random().nextInt(100);
    }
}
```
```java
// En Activity
MyBoundService mService;
boolean mBound = false;

private ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
        MyBoundService.LocalBinder binder = (MyBoundService.LocalBinder) service;
        mService = binder.getService();
        mBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0) {
        mBound = false;
    }
};

@Override
protected void onStart() {
    super.onStart();
    Intent intent = new Intent(this, MyBoundService.class);
    bindService(intent, connection, Context.BIND_AUTO_CREATE);
}

@Override
protected void onStop() {
    super.onStop();
    if (mBound) {
        unbindService(connection);
        mBound = false;
    }
}
```

---

## 2. Intents en el contexto de Broadcast  

### 2.1 Uso y opciones  
- Se envía con `Context.sendBroadcast(Intent)` o `Context.sendOrderedBroadcast(Intent, String)`.  
- Un broadcast es un mensaje que cualquier app puede recibir según permisos y filtros.  
- Usan **action** (p. ej., `"com.ejemplo.MI_ACTION"`) y pueden llevar **extras**.  
- Hay **normal broadcast** (entrega a todos los receptores) y **ordered broadcast** (uno a uno con prioridad).  
- Puedes usar permisos para restringir emisores o receptores.  

### 2.2 Opciones comunes del Intent para Broadcast  
- **Action**: describe el evento (por ejemplo `Intent.ACTION_AIRPLANE_MODE_CHANGED`).  
- **Extras**: información adicional.  
- **Package / Component**: restringe destino (`intent.setPackage(...)`).  
- **Permission**: restringe acceso.  
- **Ordered vs Normal**: define comportamiento de entrega.  

### 2.3 Ejemplos en Java  

#### Ejemplo A: Envío de broadcast simple  
```java
// En Activity o Service
Intent intent = new Intent("com.ejemplo.app.ACTION_ACTUALIZACION_DATOS");
intent.putExtra("extra_dato", "valor");
intent.setPackage(getPackageName());
sendBroadcast(intent);
```
```java
// MyBroadcastReceiver.java
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.ejemplo.app.ACTION_ACTUALIZACION_DATOS".equals(intent.getAction())) {
            String valor = intent.getStringExtra("extra_dato");
        }
    }
}
```
```xml
<!-- AndroidManifest.xml -->
<receiver android:name=".MyBroadcastReceiver">
    <intent-filter>
        <action android:name="com.ejemplo.app.ACTION_ACTUALIZACION_DATOS" />
    </intent-filter>
</receiver>
```

#### Ejemplo B: Broadcast ordenado con permiso  
```java
Intent intent = new Intent("com.ejemplo.app.ACTION_PROCESO_COMPLETADO");
intent.putExtra("resultado", true);
sendOrderedBroadcast(intent, "com.ejemplo.app.PERMISSION_RECEIVE_BROADCAST");
```
```java
public class ResultadoReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean resultado = intent.getBooleanExtra("resultado", false);
        abortBroadcast();
    }
}
```
```xml
<permission android:name="com.ejemplo.app.PERMISSION_RECEIVE_BROADCAST"
            android:protectionLevel="normal" />

<receiver android:name=".ResultadoReceiver"
          android:permission="com.ejemplo.app.PERMISSION_RECEIVE_BROADCAST">
    <intent-filter android:priority="100">
        <action android:name="com.ejemplo.app.ACTION_PROCESO_COMPLETADO" />
    </intent-filter>
</receiver>
```

---

## 3. Bibliografía  

- Android Developers. (2025). *Services overview | Background work*. Recuperado de [https://developer.android.com/develop/background-work/services](https://developer.android.com/develop/background-work/services)  
- Android Developers. (2025). *Broadcasts overview | Background work*. Recuperado de [https://developer.android.com/develop/background-work/background-tasks/broadcasts](https://developer.android.com/develop/background-work/background-tasks/broadcasts)  
- Android Developers. (2025). *System Broadcast Intents (API Level 30)*. Recuperado de [https://developer.android.com/about/versions/11/reference/broadcast-intents-30](https://developer.android.com/about/versions/11/reference/broadcast-intents-30)  
- GeeksforGeeks. (2024). *Broadcast Receiver in Android with Example*. Recuperado de [https://www.geeksforgeeks.org/broadcast-receiver-in-android-with-example/](https://www.geeksforgeeks.org/broadcast-receiver-in-android-with-example/)  
- Android Academy. (2024). *Using Intents to Broadcast Events*. Recuperado de [https://it.aduacademy.in/Android/chapter5/Using%20Intents%20to%20Broadcast%20Events.pdf](https://it.aduacademy.in/Android/chapter5/Using%20Intents%20to%20Broadcast%20Events.pdf)
