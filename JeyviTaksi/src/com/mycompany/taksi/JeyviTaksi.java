package com.mycompany.taksi; //classlarımızı içinde bulunduran paketimizin ismi

import java.util.ArrayList; //arraylist kullanabilmek için import etmemiz gerekiyor
import java.util.HashMap; //hashmap kullanabilmek için import ettik (hashmap sözlük gibi)
import java.util.Map; //map kullanabilmek için import ettiik
import java.util.Scanner; //scanner kullanabilmek için import ettik

class Surucu { // sürücü sınıfımızı oluşturuyoruz burada bir dizi içinde isim, plaka, puan bilgilerini girmek istiyoruz
    String isim;
    String plaka;
    double puan;

    Surucu(String isim, String plaka, double puan) { //sürücü bir constructor new surucu diyip içine taksicilerimizi gireceğiz
        this.isim = isim; //metodun adı constructor nesne ilk üretildiği zaman çalışır
        this.plaka = plaka;    //this dediğimizde ise üretilen nesneyi verir
        this.puan = puan;       //burada üretilen nesnenin değerini giriyoruz
    }
}


class Durak {
    String isim;
    int konum;
    ArrayList<Surucu> suruculer;

    public Durak(String isim, int konum) { //arraylistimize gireceğimiz değişkenleri içinde barındıran metod
        this.isim = isim;
        this.konum = konum;
        this.suruculer = new ArrayList<>();
    }

    public void SurucuEkle(Surucu surucu) {
        this.suruculer.add(surucu);
    } //surucu ekle metoduyla arraylistimize sürücüler
    //ekleyeceğiz
}

class GaziTaksi extends Durak { //durak sınıfındaki dizimize değer atayacağımız için extend ettik
    //Gazi taksi durağı ile ilgili bilgileri içinde bulundurması için tanımladığım sınıf
    public GaziTaksi() {
        super("Gazi Taksi", 0);}   /*bir alt-sınıf, super() metodunu kullanarak, üst sınıfının
     bir nesnesini yaratabilir ve onun değişkenlerine değer atayabilir*/
} //böylece biz durak classımıza gazi taksi durağını ve gazi mahallesi konumunu 0. konum olarak göndermiş olduk

class GayeTaksi extends Durak{
    public GayeTaksi() {
        super("Gaye Taksi", 0);
    }
}

class BesevlerTaksi extends Durak{
    public BesevlerTaksi() {
        super("Beşevler Taksi", 1);
    }
}

class BesevlerTeknikTaksi extends Durak {
    public BesevlerTeknikTaksi() {
        super("Beşevler Teknik Taksi", 1);
    }
}


class BahceliTaksi extends Durak{
    public BahceliTaksi() {
        super("Bahçeli Taksi", 2);
    }
}

class CarsiTaksi extends Durak {
    public CarsiTaksi() {
        super("Çarşı Taksi", 2);
    }
}

class KizilayTaksi extends Durak {
    public KizilayTaksi() {
        super("Kızılay Taksi", 3);
    }
}

class IhlamurTaksi extends Durak {
    public IhlamurTaksi() {
        super("Ihlamur Taksi", 3);
    }
}

class TunaliTaksi extends Durak {
    public TunaliTaksi() {
        super("Tunalı Taksi", 4);
    }
}

class BestekarTaksi extends Durak {
    public BestekarTaksi() {
        super("Bestekar Taksi", 4);
    }
}

class IcCebeciTaksi extends Durak {
    public IcCebeciTaksi() {
        super("İç Cebeci Taksi", 5);
    }
}

class HukukTaksi extends Durak {
    public HukukTaksi() {
        super("Hukuk Taksi", 5);
    }
}


public class JeyviTaksi {
    static final int V = 6;
    // Graf komşuluk matrisi
    // 0 -> Gazi Mahallesi, 1 -> Beşevler, 2 -> Bahçelievler, 3 -> Kızılay, 4 ->Tunalı, 5->Cebeci
    static double[][] graph = new double[][]{
            {0, 2.8, 4.7, 6.1, 7.7, 0}, //konumlar arasındaki mesafelerle bir 6x6 matris oluşturdum
            {2.8, 0, 2.2, 4.3, 5.5, 5.3},
            {4.7, 2.2, 0, 7.9, 6.4, 6.8},
            {6.1, 4.3, 7.9, 0, 1.9, 2.6},
            {7.7, 5.5, 6.4, 1.9, 0, 4.0},
            {0, 5.3, 6.8, 2.6, 4.0, 0} // gazi mahallesi ve cebeci arasında direkt bir yol bulunmadığı için 0 girdim
    };

    //dijkstra algoritması
    static int minDistance(double[] mesafe, Boolean[] sptSet) {
        double min = Double.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < V; v++)
            if (!sptSet[v] && mesafe[v] <= min) {
                min = mesafe[v];
                min_index = v;
            }

        return min_index;
    }

    // Grafta en kısa yolu bulma algoritması bkz. Dijkstra Algoritması
    static double dijkstra(int baslangic, int bitis) { //kullanmamın sebebi gazi mahallesi ve cebeci arasında direkt bir yol bulunmaması
        double[] mesafe = new double[V];
        Boolean[] sptSet = new Boolean[V];

        for (int i = 0; i < V; i++) {
            mesafe[i] = Double.MAX_VALUE;
            sptSet[i] = false;
        }

        mesafe[baslangic] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(mesafe, sptSet);
            sptSet[u] = true;

            for (int v = 0; v < V; v++)
                if (!sptSet[v] && graph[u][v] != 0 &&
                        mesafe[u] != Integer.MAX_VALUE && mesafe[u] + graph[u][v] < mesafe[v])
                    mesafe[v] = mesafe[u] + graph[u][v];
        }
        return mesafe[bitis];
    }

    public static void main(String[] args) {
        GaziTaksi gazitaksi = new GaziTaksi(); //sınıfımızı tanımladık
        gazitaksi.SurucuEkle(new Surucu("AHMET YILMAZ", "06 JMT 127", 3.5)); //arraylistimize sürücü eklemek için
        gazitaksi.SurucuEkle(new Surucu("SÜLEYMAN YAVUZ", "06 JMV 127", 4.1));//metodumuzu kullandık
        gazitaksi.SurucuEkle(new Surucu("SELİM ŞİMŞEK", "06 JMB 127", 3.9));
        gazitaksi.SurucuEkle(new Surucu("ALİ KARA", "06 JMY 127", 1.2));

        GayeTaksi gayetaksi = new GayeTaksi();
        gayetaksi.SurucuEkle(new Surucu("ASAF MİRAÇ", "06 T 137", 2.5));
        gayetaksi.SurucuEkle(new Surucu("EKREM ÇELEBİ", "06 T 147", 3.2));
        gayetaksi.SurucuEkle(new Surucu("ONUR ARSLAN", "06 T 187", 4.0));
        gayetaksi.SurucuEkle(new Surucu("AHMET DOST", "06 T 177", 1.3));

        BesevlerTaksi besevlertaksi = new BesevlerTaksi();
        besevlertaksi.SurucuEkle(new Surucu("FATİH SEMİH", "06 JMB 127", 3.0));
        besevlertaksi.SurucuEkle(new Surucu("NURİ KOR"," 06 JMT 127",4.2));
        besevlertaksi.SurucuEkle(new Surucu("SEYFİ YAVUZ" ,"06 JMV 127", 1.9));

        BesevlerTeknikTaksi besevlertekniktaksi = new BesevlerTeknikTaksi();
        besevlertekniktaksi.SurucuEkle(new Surucu("KERİM KAYA"," 06 Y 127", 3.1));
        besevlertekniktaksi.SurucuEkle(new Surucu("UFUK EROĞLU", "06 V 127", 4.6));

        BahceliTaksi bahcelitaksi = new BahceliTaksi();
        bahcelitaksi.SurucuEkle(new Surucu("HASAN DÜZ", "06 J 127",  4.5));
        bahcelitaksi.SurucuEkle(new Surucu("FATMA YAVUZ", "06 JT 177 ",  1.8));
        bahcelitaksi.SurucuEkle(new Surucu("ARİF AL", "06 JMB 117 ",  2.9));
        bahcelitaksi.SurucuEkle(new Surucu("FIRAT KALKAN" ,"06 MS 127 ",  3.7));

        CarsiTaksi carsitaksi = new CarsiTaksi();
        carsitaksi.SurucuEkle(new Surucu("ABDULLAH IŞIK", "06 KT 127",  3.4));
        carsitaksi.SurucuEkle(new Surucu("MUSTAFA ELGÜL", "06 PV 127", 2.8));

        KizilayTaksi kizilaytaksi = new KizilayTaksi();
        kizilaytaksi.SurucuEkle(new Surucu("HALUK YILMAZ", "06 MT 127", 1.5));
        kizilaytaksi.SurucuEkle(new Surucu("SİNAN YAĞMUR", "06 MV 127",  4.1));
        kizilaytaksi.SurucuEkle(new Surucu("RIZA DERE", "06 MB 127",  4.9));
        kizilaytaksi.SurucuEkle(new Surucu("MAHMUT KAR" ,"06 MY 127 ",  3.3));

        IhlamurTaksi ihlamurtaksi = new IhlamurTaksi();
        ihlamurtaksi.SurucuEkle(new Surucu("SERKAN TOKA", "06 MT 827",3.6));

        TunaliTaksi tunalitaksi = new TunaliTaksi();
        tunalitaksi.SurucuEkle(new Surucu("TARIK GÜÇ", "06 FT 127", 3.9));
        tunalitaksi.SurucuEkle(new Surucu("AYŞE SERİN", "06 JMV 1927",  1.8));
        tunalitaksi.SurucuEkle(new Surucu("ADEM TAN", "06 JMB 197", 2.9));
        tunalitaksi.SurucuEkle(new Surucu("ÖMER YILDIZ", "06 JMY 123 ", 4.7));

        BestekarTaksi bestekartaksi = new BestekarTaksi();
        bestekartaksi.SurucuEkle(new Surucu("YUSUF ARSLAN", " 06 JMT 1787", 1.5));
        bestekartaksi.SurucuEkle(new Surucu("DENİZ GARİP" ," 06 JMO 137 ",  3.8));
        bestekartaksi.SurucuEkle(new Surucu("SEBAHAT GÜNOL ", "06 JMB 1997 ",  2.3));

        IcCebeciTaksi iccebecitaksi = new IcCebeciTaksi();
        iccebecitaksi.SurucuEkle(new Surucu("ZEKİ AKYİĞİT ","06 JMT 1117",  4.3));
        iccebecitaksi.SurucuEkle(new Surucu("TEVFİK ÜNLÜ ", "06 JPV 127 ", 4.6));

        HukukTaksi hukuktaksi = new HukukTaksi();
        hukuktaksi.SurucuEkle(new Surucu("BURAK GÜNEŞ", "06 JIT 1127", 5.0));
        hukuktaksi.SurucuEkle(new Surucu("BAHADIR AKGÜN", "06 JKV 1227", 3.2));
        hukuktaksi.SurucuEkle(new Surucu("BERKANT ŞAHİN", "06 JHB 1237", 3.1));


        Map<Integer, ArrayList<Durak>> hm = new HashMap<>(); //konumları içinde saklayacak olan hm'i tanımlıyoruz
        //hashmap ve map arasında bir fark yok sadece map kullanmak daha avantajlı
        ArrayList<Durak> tmp = new ArrayList<>(); //durakları içinde saklayacak olan tmp'yi tanımlıyoruz
        tmp.add(gazitaksi);
        tmp.add(gayetaksi); //arraylistimizde gazi mahallesi kısmına(0. konum) durak ekliyoruz
        hm.put(0, tmp); //put mapi mapin içine koymak için kullanılır

        tmp = new ArrayList<>();
        tmp.add(besevlertaksi); //arraylistimizde beşevler kısmına(1. konum) durak ekliyoruz
        tmp.add(besevlertekniktaksi);
        hm.put(1, tmp);

        tmp = new ArrayList<>();
        tmp.add(bahcelitaksi); //arraylistimizde bahçelievler kısmına(2. konum) durak ekliyoruz
        tmp.add(carsitaksi);
        hm.put(2, tmp);

        tmp = new ArrayList<>();
        tmp.add(kizilaytaksi); //arraylistimizde kızılay kısmına(3. konum) durak ekliyoruz
        tmp.add(ihlamurtaksi);
        hm.put(3, tmp);

        tmp = new ArrayList<>();
        tmp.add(tunalitaksi); //arraylistimizde tunalı kısmına(4. konum) durak ekliyoruz
        tmp.add(bestekartaksi);
        hm.put(4, tmp);

        tmp = new ArrayList<>();
        tmp.add(iccebecitaksi); //arraylistimizde cebeci kısmına(5. konum) durak ekliyoruz
        tmp.add(hukuktaksi);
        hm.put(5, tmp); //put mapi mapin içine koymak için kullanılır

        Scanner s = new Scanner(System.in);
        String secim = "m";
        int kmKatsayi = 8; // 2022 yılında Ankaradaki km başına taksi ücreti 8 tl'dir.
        int acilis = 10; // 2022  yılında Ankaradaki taksi açılış ücreti 10 tl'dir.

        do {
            System.out.println("**************************************************");
            System.out.println("*********************JEYVİ*************************");
            System.out.println("**********TAKSİ UYGULAMASINA HOŞGELDİNİZ**********");
            System.out.println("**************************************************");
            System.out.println("**************************************************");
            System.out.println("LÜTFEN BULUNDUĞUNUZ KONUMU SEÇİNİZ:");
            System.out.println("1.) Gazi Mahallesi\n2.) Beşevler\n3.) Bahçelievler\n4.) Kızılay\n5.) Tunalı\n6.) Cebeci");

            int konumsecim = s.nextInt();

            if (konumsecim > V || konumsecim < 1) {
                System.out.println("Hatalı seçim. Program yeniden başlatılıyor...");
                continue;
            }

            konumsecim -= 1;


            System.out.println("Bulunduğunuz Konumdaki Uygun Duraklar Aşağıda Listelenmiştir:");
            for (int i = 0; i < hm.get(konumsecim).size(); i++) {
                System.out.println((i + 1) + ".) " + hm.get(konumsecim).get(i).isim);//i. indexteki durakları listeleyen for döngüsü
            } //
            System.out.println("Durak seçimi: ");
            int duraksecim = s.nextInt();
            if (duraksecim > hm.get(konumsecim).size() || duraksecim < 1) { //eğer seçilen sayı durak sayısından fazlaysa uygulama baştan başlatılır
                System.out.println("Hatalı seçim. Program yeniden başlatılıyor...");
                continue;
            }

            duraksecim -= 1; //dizilerde 0. indexten başlandığı için
            Durak d = hm.get(konumsecim).get(duraksecim); //hm bir hashmap
            //integer olarak konumu girince o konumdaki durakların listesini getiriyor

            System.out.println("Uygun Sürücüler:");
            for (int i = 0; i < d.suruculer.size(); i++) {
                System.out.println((i + 1) + ".) " + d.suruculer.get(i).isim + " " + d.suruculer.get(i).plaka); //i. indexteki sürücüleri listeler
            }

            int surucusecim = s.nextInt();
            if (surucusecim > d.suruculer.size() || surucusecim < 1) { //eğer seçilen sayı sürücü sayısından fazlaysa uygulama baştan başlatılır
                System.out.println("Hatalı seçim. Program yeniden başlatılıyor...");
                continue; //continue burada programı baştan başlatıyor
            }
            surucusecim -= 1; //dizilerde 0. indexten başlandığı için

            Surucu surucu = d.suruculer.get(surucusecim);

            System.out.println("1.) Gazi Mahallesi\n2.) Beşevler\n3.) Bahçelievler\n4.) Kızılay\n5.) Tunalı\n6.) Cebeci");
            System.out.println("GİTMEK İSTEDİĞİNİZ KONUMU SEÇİNİZ:");
            int hedefsecim = s.nextInt();
            if (hedefsecim > V || hedefsecim < 1) { // v burada konum sayısını temsil ediyor
                // eğer seçtiğimiz sayı v'den büyük olursa uygulama yeniden başlıyor
                System.out.println("Hatalı seçim. Program yeniden başlatılıyor...");
                continue;
            }
            hedefsecim -= 1; //dizilerde 0. indexten başlandığı için

            double min_uzaklik = dijkstra(konumsecim, hedefsecim); //aradaki en kısa uzaklığı bulabilmek için dijkstra kullanıyoruz
            double ucret = acilis + (kmKatsayi * min_uzaklik); //fiyat hesabı

            System.out.println("Uzaklik: " + min_uzaklik + "km ");
            double odenen,paraustu; //paraüstü hesaplayabilmek için vermek istediğiniz para miktarını giriyorsunuz
            do {
                System.out.println("Ödemeniz gereken miktar: " + ucret + "TL'dir.");
                System.out.println("Ödeme yapmak istediğiniz miktarı giriniz");
                odenen = s.nextDouble();
            }
            while (odenen < ucret);

            paraustu = odenen - ucret;
            System.out.println("Paraüstünüz: " + paraustu + "TL'dir.");
            System.out.println("****************************\n****************************\n**************************");
            System.out.println("Sürücüye 5 üzerinden puanınız: ");
            double puan = s.nextInt();

            System.out.println("Sürücünün eski puanı: " + surucu.puan);
            surucu.puan = (surucu.puan + puan) / 2; //puanı sürücünün ilk puanı ve sizin verdiğiniz puanla toplayıp ortalamasını alarak hesaplıyoruz
            System.out.println("Sürücünün yeni puanı: " + surucu.puan);

            System.out.println("Çıkmak için E tuşuna basınız.");
            System.out.println("Ana menüye dönmek için M tuşuna basınız");
            secim = s.next();
            if (secim.equalsIgnoreCase("e")) {
                System.exit(0); //çıkış yapmak için e'ye basıldıktan sonra program kapatılıyor
            }
        }
        while (secim.equalsIgnoreCase("m")); //tekrar ana menüye dönmek için döngüye sokuyoruz eğer m'ye basarsak her şeyi en baştan yapacak
        //ana menüye dönebilmek için de do while döngüsü ekledim
    }
}