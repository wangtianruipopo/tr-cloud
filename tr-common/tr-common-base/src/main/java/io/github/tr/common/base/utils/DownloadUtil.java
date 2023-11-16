package io.github.tr.common.base.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
public class DownloadUtil {

    public static final Map<String, String> ALL_MIMES = new HashMap<>();

    static {
                ALL_MIMES.put(".323", "text/h323");

                ALL_MIMES.put(".3gp", "video/3gpp");

                ALL_MIMES.put(".aab", "application/x-authoware-bin");

                ALL_MIMES.put(".aam", "application/x-authoware-map");

                ALL_MIMES.put(".aas", "application/x-authoware-seg");

                ALL_MIMES.put(".acx", "application/internet-property-stream");

                ALL_MIMES.put(".ai", "application/postscript");

                ALL_MIMES.put(".aif", "audio/x-aiff");

                ALL_MIMES.put(".aifc", "audio/x-aiff");

                ALL_MIMES.put(".aiff", "audio/x-aiff");

                ALL_MIMES.put(".als", "audio/X-Alpha5");

                ALL_MIMES.put(".amc", "application/x-mpeg");

                ALL_MIMES.put(".ani", "application/octet-stream");

                ALL_MIMES.put(".apk", "application/vnd.android.package-archive");

                ALL_MIMES.put(".asc", "text/plain");

                ALL_MIMES.put(".asd", "application/astound");

                ALL_MIMES.put(".asf", "video/x-ms-asf");

                ALL_MIMES.put(".asn", "application/astound");

                ALL_MIMES.put(".asp", "application/x-asap");

                ALL_MIMES.put(".asr", "video/x-ms-asf");

                ALL_MIMES.put(".asx", "video/x-ms-asf");

                ALL_MIMES.put(".au", "audio/basic");

                ALL_MIMES.put(".avb", "application/octet-stream");

                ALL_MIMES.put(".avi", "video/x-msvideo");

                ALL_MIMES.put(".awb", "audio/amr-wb");

                ALL_MIMES.put(".axs", "application/olescript");

                ALL_MIMES.put(".bas", "text/plain");

                ALL_MIMES.put(".bcpio", "application/x-bcpio");

                ALL_MIMES.put(".bin", "application/octet-stream");

                ALL_MIMES.put(".bld", "application/bld");

                ALL_MIMES.put(".bld2", "application/bld2");

                ALL_MIMES.put(".bmp", "image/bmp");

                ALL_MIMES.put(".bpk", "application/octet-stream");

                ALL_MIMES.put(".bz2", "application/x-bzip2");

                ALL_MIMES.put(".c", "text/plain");

                ALL_MIMES.put(".cal", "image/x-cals");

                ALL_MIMES.put(".cat", "application/vnd.ms-pkiseccat");

                ALL_MIMES.put(".ccn", "application/x-cnc");

                ALL_MIMES.put(".cco", "application/x-cocoa");

                ALL_MIMES.put(".cdf", "application/x-cdf");

                ALL_MIMES.put(".cer", "application/x-x509-ca-cert");

                ALL_MIMES.put(".cgi", "magnus-internal/cgi");

                ALL_MIMES.put(".chat", "application/x-chat");

                ALL_MIMES.put(".class", "application/octet-stream");

                ALL_MIMES.put(".clp", "application/x-msclip");

                ALL_MIMES.put(".cmx", "image/x-cmx");

                ALL_MIMES.put(".co", "application/x-cult3d-object");

                ALL_MIMES.put(".cod", "image/cis-cod");

                ALL_MIMES.put(".conf", "text/plain");

                ALL_MIMES.put(".cpio", "application/x-cpio");

                ALL_MIMES.put(".cpp", "text/plain");

                ALL_MIMES.put(".cpt", "application/mac-compactpro");

                ALL_MIMES.put(".crd", "application/x-mscardfile");

                ALL_MIMES.put(".crl", "application/pkix-crl");

                ALL_MIMES.put(".crt", "application/x-x509-ca-cert");

                ALL_MIMES.put(".csh", "application/x-csh");

                ALL_MIMES.put(".csm", "chemical/x-csml");

                ALL_MIMES.put(".csml", "chemical/x-csml");

                ALL_MIMES.put(".css", "text/css");

                ALL_MIMES.put(".cur", "application/octet-stream");

                ALL_MIMES.put(".dcm", "x-lml/x-evm");

                ALL_MIMES.put(".dcr", "application/x-director");

                ALL_MIMES.put(".dcx", "image/x-dcx");

                ALL_MIMES.put(".der", "application/x-x509-ca-cert");

                ALL_MIMES.put(".dhtml", "text/html");

                ALL_MIMES.put(".dir", "application/x-director");

                ALL_MIMES.put(".dll", "application/x-msdownload");

                ALL_MIMES.put(".dmg", "application/octet-stream");

                ALL_MIMES.put(".dms", "application/octet-stream");

                ALL_MIMES.put(".doc", "application/msword");

                ALL_MIMES.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

                ALL_MIMES.put(".dot", "application/msword");

                ALL_MIMES.put(".dvi", "application/x-dvi");

                ALL_MIMES.put(".dwf", "drawing/x-dwf");

                ALL_MIMES.put(".dwg", "application/x-autocad");

                ALL_MIMES.put(".dxf", "application/x-autocad");

                ALL_MIMES.put(".dxr", "application/x-director");

                ALL_MIMES.put(".ebk", "application/x-expandedbook");

                ALL_MIMES.put(".emb", "chemical/x-embl-dl-nucleotide");

                ALL_MIMES.put(".embl", "chemical/x-embl-dl-nucleotide");

                ALL_MIMES.put(".eps", "application/postscript");

                ALL_MIMES.put(".epub", "application/epub+zip");

                ALL_MIMES.put(".eri", "image/x-eri");

                ALL_MIMES.put(".es", "audio/echospeech");

                ALL_MIMES.put(".esl", "audio/echospeech");

                ALL_MIMES.put(".etc", "application/x-earthtime");

                ALL_MIMES.put(".etx", "text/x-setext");

                ALL_MIMES.put(".evm", "x-lml/x-evm");

                ALL_MIMES.put(".evy", "application/envoy");

                ALL_MIMES.put(".exe", "application/octet-stream");

                ALL_MIMES.put(".fh4", "image/x-freehand");

                ALL_MIMES.put(".fh5", "image/x-freehand");

                ALL_MIMES.put(".fhc", "image/x-freehand");

                ALL_MIMES.put(".fif", "application/fractals");

                ALL_MIMES.put(".flr", "x-world/x-vrml");

                ALL_MIMES.put(".flv", "flv-application/octet-stream");

                ALL_MIMES.put(".fm", "application/x-maker");

                ALL_MIMES.put(".fpx", "image/x-fpx");

                ALL_MIMES.put(".fvi", "video/isivideo");

                ALL_MIMES.put(".gau", "chemical/x-gaussian-input");

                ALL_MIMES.put(".gca", "application/x-gca-compressed");

                ALL_MIMES.put(".gdb", "x-lml/x-gdb");

                ALL_MIMES.put(".gif", "image/gif");

                ALL_MIMES.put(".gps", "application/x-gps");

                ALL_MIMES.put(".gtar", "application/x-gtar");

                ALL_MIMES.put(".gz", "application/x-gzip");

                ALL_MIMES.put(".h", "text/plain");

                ALL_MIMES.put(".hdf", "application/x-hdf");

                ALL_MIMES.put(".hdm", "text/x-hdml");

                ALL_MIMES.put(".hdml", "text/x-hdml");

                ALL_MIMES.put(".hlp", "application/winhlp");

                ALL_MIMES.put(".hqx", "application/mac-binhex40");

                ALL_MIMES.put(".hta", "application/hta");

                ALL_MIMES.put(".htc", "text/x-component");

                ALL_MIMES.put(".htm", "text/html");

                ALL_MIMES.put(".html", "text/html");

                ALL_MIMES.put(".hts", "text/html");

                ALL_MIMES.put(".htt", "text/webviewhtml");

                ALL_MIMES.put(".ice", "x-conference/x-cooltalk");

                ALL_MIMES.put(".ico", "image/x-icon");

                ALL_MIMES.put(".ief", "image/ief");

                ALL_MIMES.put(".ifm", "image/gif");

                ALL_MIMES.put(".ifs", "image/ifs");

                ALL_MIMES.put(".iii", "application/x-iphone");

                ALL_MIMES.put(".imy", "audio/melody");

                ALL_MIMES.put(".ins", "application/x-internet-signup");

                ALL_MIMES.put(".ips", "application/x-ipscript");

                ALL_MIMES.put(".ipx", "application/x-ipix");

                ALL_MIMES.put(".isp", "application/x-internet-signup");

                ALL_MIMES.put(".it", "audio/x-mod");

                ALL_MIMES.put(".itz", "audio/x-mod");

                ALL_MIMES.put(".ivr", "i-world/i-vrml");

                ALL_MIMES.put(".j2k", "image/j2k");

                ALL_MIMES.put(".jad", "text/vnd.sun.j2me.app-descriptor");

                ALL_MIMES.put(".jam", "application/x-jam");

                ALL_MIMES.put(".jar", "application/java-archive");

                ALL_MIMES.put(".java", "text/plain");

                ALL_MIMES.put(".jfif", "image/pipeg");

                ALL_MIMES.put(".jnlp", "application/x-java-jnlp-file");

                ALL_MIMES.put(".jpe", "image/jpeg");

                ALL_MIMES.put(".jpeg", "image/jpeg");

                ALL_MIMES.put(".jpg", "image/jpeg");

                ALL_MIMES.put(".jpz", "image/jpeg");

                ALL_MIMES.put(".js", "application/x-javascript");

                ALL_MIMES.put(".jwc", "application/jwc");

                ALL_MIMES.put(".kjx", "application/x-kjx");

                ALL_MIMES.put(".lak", "x-lml/x-lak");

                ALL_MIMES.put(".latex", "application/x-latex");

                ALL_MIMES.put(".lcc", "application/fastman");

                ALL_MIMES.put(".lcl", "application/x-digitalloca");

                ALL_MIMES.put(".lcr", "application/x-digitalloca");

                ALL_MIMES.put(".lgh", "application/lgh");

                ALL_MIMES.put(".lha", "application/octet-stream");

                ALL_MIMES.put(".lml", "x-lml/x-lml");

                ALL_MIMES.put(".lmlpack", "x-lml/x-lmlpack");

                ALL_MIMES.put(".log", "text/plain");

                ALL_MIMES.put(".lsf", "video/x-la-asf");

                ALL_MIMES.put(".lsx", "video/x-la-asf");

                ALL_MIMES.put(".lzh", "application/octet-stream");

                ALL_MIMES.put(".m13", "application/x-msmediaview");

                ALL_MIMES.put(".m14", "application/x-msmediaview");

                ALL_MIMES.put(".m15", "audio/x-mod");

                ALL_MIMES.put(".m3u", "audio/x-mpegurl");

                ALL_MIMES.put(".m3url", "audio/x-mpegurl");

                ALL_MIMES.put(".m4a", "audio/mp4a-latm");

                ALL_MIMES.put(".m4b", "audio/mp4a-latm");

                ALL_MIMES.put(".m4p", "audio/mp4a-latm");

                ALL_MIMES.put(".m4u", "video/vnd.mpegurl");

                ALL_MIMES.put(".m4v", "video/x-m4v");

                ALL_MIMES.put(".ma1", "audio/ma1");

                ALL_MIMES.put(".ma2", "audio/ma2");

                ALL_MIMES.put(".ma3", "audio/ma3");

                ALL_MIMES.put(".ma5", "audio/ma5");

                ALL_MIMES.put(".man", "application/x-troff-man");

                ALL_MIMES.put(".map", "magnus-internal/imagemap");

                ALL_MIMES.put(".mbd", "application/mbedlet");

                ALL_MIMES.put(".mct", "application/x-mascot");

                ALL_MIMES.put(".mdb", "application/x-msaccess");

                ALL_MIMES.put(".mdz", "audio/x-mod");

                ALL_MIMES.put(".me", "application/x-troff-me");

                ALL_MIMES.put(".mel", "text/x-vmel");

                ALL_MIMES.put(".mht", "message/rfc822");

                ALL_MIMES.put(".mhtml", "message/rfc822");

                ALL_MIMES.put(".mi", "application/x-mif");

                ALL_MIMES.put(".mid", "audio/mid");

                ALL_MIMES.put(".midi", "audio/midi");

                ALL_MIMES.put(".mif", "application/x-mif");

                ALL_MIMES.put(".mil", "image/x-cals");

                ALL_MIMES.put(".mio", "audio/x-mio");

                ALL_MIMES.put(".mmf", "application/x-skt-lbs");

                ALL_MIMES.put(".mng", "video/x-mng");

                ALL_MIMES.put(".mny", "application/x-msmoney");

                ALL_MIMES.put(".moc", "application/x-mocha");

                ALL_MIMES.put(".mocha", "application/x-mocha");

                ALL_MIMES.put(".mod", "audio/x-mod");

                ALL_MIMES.put(".mof", "application/x-yumekara");

                ALL_MIMES.put(".mol", "chemical/x-mdl-molfile");

                ALL_MIMES.put(".mop", "chemical/x-mopac-input");

                ALL_MIMES.put(".mov", "video/quicktime");

                ALL_MIMES.put(".movie", "video/x-sgi-movie");

                ALL_MIMES.put(".mp2", "video/mpeg");

                ALL_MIMES.put(".mp3", "audio/mpeg");

                ALL_MIMES.put(".mp4", "video/mp4");

                ALL_MIMES.put(".mpa", "video/mpeg");

                ALL_MIMES.put(".mpc", "application/vnd.mpohun.certificate");

                ALL_MIMES.put(".mpe", "video/mpeg");

                ALL_MIMES.put(".mpeg", "video/mpeg");

                ALL_MIMES.put(".mpg", "video/mpeg");

                ALL_MIMES.put(".mpg4", "video/mp4");

                ALL_MIMES.put(".mpga", "audio/mpeg");

                ALL_MIMES.put(".mpn", "application/vnd.mophun.application");

                ALL_MIMES.put(".mpp", "application/vnd.ms-project");

                ALL_MIMES.put(".mps", "application/x-mapserver");

                ALL_MIMES.put(".mpv2", "video/mpeg");

                ALL_MIMES.put(".mrl", "text/x-mrml");

                ALL_MIMES.put(".mrm", "application/x-mrm");

                ALL_MIMES.put(".ms", "application/x-troff-ms");

                ALL_MIMES.put(".msg", "application/vnd.ms-outlook");

                ALL_MIMES.put(".mts", "application/metastream");

                ALL_MIMES.put(".mtx", "application/metastream");

                ALL_MIMES.put(".mtz", "application/metastream");

                ALL_MIMES.put(".mvb", "application/x-msmediaview");

                ALL_MIMES.put(".mzv", "application/metastream");

                ALL_MIMES.put(".nar", "application/zip");

                ALL_MIMES.put(".nbmp", "image/nbmp");

                ALL_MIMES.put(".nc", "application/x-netcdf");

                ALL_MIMES.put(".ndb", "x-lml/x-ndb");

                ALL_MIMES.put(".ndwn", "application/ndwn");

                ALL_MIMES.put(".nif", "application/x-nif");

                ALL_MIMES.put(".nmz", "application/x-scream");

                ALL_MIMES.put(".nokia-op-logo", "image/vnd.nok-oplogo-color");

                ALL_MIMES.put(".npx", "application/x-netfpx");

                ALL_MIMES.put(".nsnd", "audio/nsnd");

                ALL_MIMES.put(".nva", "application/x-neva1");

                ALL_MIMES.put(".nws", "message/rfc822");

                ALL_MIMES.put(".oda", "application/oda");

                ALL_MIMES.put(".ogg", "audio/ogg");

                ALL_MIMES.put(".oom", "application/x-AtlasMate-Plugin");

                ALL_MIMES.put(".p10", "application/pkcs10");

                ALL_MIMES.put(".p12", "application/x-pkcs12");

                ALL_MIMES.put(".p7b", "application/x-pkcs7-certificates");

                ALL_MIMES.put(".p7c", "application/x-pkcs7-mime");

                ALL_MIMES.put(".p7m", "application/x-pkcs7-mime");

                ALL_MIMES.put(".p7r", "application/x-pkcs7-certreqresp");

                ALL_MIMES.put(".p7s", "application/x-pkcs7-signature");

                ALL_MIMES.put(".pac", "audio/x-pac");

                ALL_MIMES.put(".pae", "audio/x-epac");

                ALL_MIMES.put(".pan", "application/x-pan");

                ALL_MIMES.put(".pbm", "image/x-portable-bitmap");

                ALL_MIMES.put(".pcx", "image/x-pcx");

                ALL_MIMES.put(".pda", "image/x-pda");

                ALL_MIMES.put(".pdb", "chemical/x-pdb");

                ALL_MIMES.put(".pdf", "application/pdf");

                ALL_MIMES.put(".pfr", "application/font-tdpfr");

                ALL_MIMES.put(".pfx", "application/x-pkcs12");

                ALL_MIMES.put(".pgm", "image/x-portable-graymap");

                ALL_MIMES.put(".pict", "image/x-pict");

                ALL_MIMES.put(".pko", "application/ynd.ms-pkipko");

                ALL_MIMES.put(".pm", "application/x-perl");

                ALL_MIMES.put(".pma", "application/x-perfmon");

                ALL_MIMES.put(".pmc", "application/x-perfmon");

                ALL_MIMES.put(".pmd", "application/x-pmd");

                ALL_MIMES.put(".pml", "application/x-perfmon");

                ALL_MIMES.put(".pmr", "application/x-perfmon");

                ALL_MIMES.put(".pmw", "application/x-perfmon");

                ALL_MIMES.put(".png", "image/png");

                ALL_MIMES.put(".pnm", "image/x-portable-anymap");

                ALL_MIMES.put(".pnz", "image/png");

                ALL_MIMES.put(".pot,", "application/vnd.ms-powerpoint");

                ALL_MIMES.put(".ppm", "image/x-portable-pixmap");

                ALL_MIMES.put(".pps", "application/vnd.ms-powerpoint");

                ALL_MIMES.put(".ppt", "application/vnd.ms-powerpoint");

                ALL_MIMES.put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");

                ALL_MIMES.put(".pqf", "application/x-cprplayer");

                ALL_MIMES.put(".pqi", "application/cprplayer");

                ALL_MIMES.put(".prc", "application/x-prc");

                ALL_MIMES.put(".prf", "application/pics-rules");

                ALL_MIMES.put(".prop", "text/plain");

                ALL_MIMES.put(".proxy", "application/x-ns-proxy-autoconfig");

                ALL_MIMES.put(".ps", "application/postscript");

                ALL_MIMES.put(".ptlk", "application/listenup");

                ALL_MIMES.put(".pub", "application/x-mspublisher");

                ALL_MIMES.put(".pvx", "video/x-pv-pvx");

                ALL_MIMES.put(".qcp", "audio/vnd.qcelp");

                ALL_MIMES.put(".qt", "video/quicktime");

                ALL_MIMES.put(".qti", "image/x-quicktime");

                ALL_MIMES.put(".qtif", "image/x-quicktime");

                ALL_MIMES.put(".r3t", "text/vnd.rn-realtext3d");

                ALL_MIMES.put(".ra", "audio/x-pn-realaudio");

                ALL_MIMES.put(".ram", "audio/x-pn-realaudio");

                ALL_MIMES.put(".rar", "application/octet-stream");
                ALL_MIMES.put(".ras", "image/x-cmu-raster");
                ALL_MIMES.put(".rc", "text/plain");
                ALL_MIMES.put(".rdf", "application/rdf+xml");
                ALL_MIMES.put(".rf", "image/vnd.rn-realflash");
                ALL_MIMES.put(".rgb", "image/x-rgb");
                ALL_MIMES.put(".rlf", "application/x-richlink");
                ALL_MIMES.put(".rm", "audio/x-pn-realaudio");
                ALL_MIMES.put(".rmf", "audio/x-rmf");
                ALL_MIMES.put(".rmi", "audio/mid");
                ALL_MIMES.put(".rmm", "audio/x-pn-realaudio");
                ALL_MIMES.put(".rmvb", "audio/x-pn-realaudio");
                ALL_MIMES.put(".rnx", "application/vnd.rn-realplayer");
                ALL_MIMES.put(".roff", "application/x-troff");
                ALL_MIMES.put(".rp", "image/vnd.rn-realpix");
                ALL_MIMES.put(".rpm", "audio/x-pn-realaudio-plugin");
                ALL_MIMES.put(".rt", "text/vnd.rn-realtext");
                ALL_MIMES.put(".rte", "x-lml/x-gps");
                ALL_MIMES.put(".rtf", "application/rtf");
                ALL_MIMES.put(".rtg", "application/metastream");
                ALL_MIMES.put(".rtx", "text/richtext");
                ALL_MIMES.put(".rv", "video/vnd.rn-realvideo");
                ALL_MIMES.put(".rwc", "application/x-rogerwilco");
                ALL_MIMES.put(".s3m", "audio/x-mod");
                ALL_MIMES.put(".s3z", "audio/x-mod");
                ALL_MIMES.put(".sca", "application/x-supercard");
                ALL_MIMES.put(".scd", "application/x-msschedule");
                ALL_MIMES.put(".sct", "text/scriptlet");
                ALL_MIMES.put(".sdf", "application/e-score");
                ALL_MIMES.put(".sea", "application/x-stuffit");
                ALL_MIMES.put(".setpay", "application/set-payment-initiation");
                ALL_MIMES.put(".setreg", "application/set-registration-initiation");
                ALL_MIMES.put(".sgm", "text/x-sgml");
                ALL_MIMES.put(".sgml", "text/x-sgml");
                ALL_MIMES.put(".sh", "application/x-sh");
                ALL_MIMES.put(".shar", "application/x-shar");
                ALL_MIMES.put(".shtml", "magnus-internal/parsed-html");
                ALL_MIMES.put(".shw", "application/presentations");
                ALL_MIMES.put(".si6", "image/si6");
                ALL_MIMES.put(".si7", "image/vnd.stiwap.sis");
                ALL_MIMES.put(".si9", "image/vnd.lgtwap.sis");
                ALL_MIMES.put(".sis", "application/vnd.symbian.install");
                ALL_MIMES.put(".sit", "application/x-stuffit");
                ALL_MIMES.put(".skd", "application/x-Koan");
                ALL_MIMES.put(".skm", "application/x-Koan");
                ALL_MIMES.put(".skp", "application/x-Koan");
                ALL_MIMES.put(".skt", "application/x-Koan");
                ALL_MIMES.put(".slc", "application/x-salsa");
                ALL_MIMES.put(".smd", "audio/x-smd");
                ALL_MIMES.put(".smi", "application/smil");
                ALL_MIMES.put(".smil", "application/smil");
                ALL_MIMES.put(".smp", "application/studiom");
                ALL_MIMES.put(".smz", "audio/x-smd");
                ALL_MIMES.put(".snd", "audio/basic");
                ALL_MIMES.put(".spc", "application/x-pkcs7-certificates");
                ALL_MIMES.put(".spl", "application/futuresplash");
                ALL_MIMES.put(".spr", "application/x-sprite");
                ALL_MIMES.put(".sprite", "application/x-sprite");
                ALL_MIMES.put(".sdp", "application/sdp");
                ALL_MIMES.put(".spt", "application/x-spt");
                ALL_MIMES.put(".src", "application/x-wais-source");
                ALL_MIMES.put(".sst", "application/vnd.ms-pkicertstore");
                ALL_MIMES.put(".stk", "application/hyperstudio");
                ALL_MIMES.put(".stl", "application/vnd.ms-pkistl");
                ALL_MIMES.put(".stm", "text/html");
                ALL_MIMES.put(".svg", "image/svg+xml");
                ALL_MIMES.put(".sv4cpio", "application/x-sv4cpio");
                ALL_MIMES.put(".sv4crc", "application/x-sv4crc");
                ALL_MIMES.put(".svf", "image/vnd");
                ALL_MIMES.put(".svh", "image/svh");
                ALL_MIMES.put(".svr", "x-world/x-svr");
                ALL_MIMES.put(".swf", "application/x-shockwave-flash");
                ALL_MIMES.put(".swfl", "application/x-shockwave-flash");
                ALL_MIMES.put(".t", "application/x-troff");
                ALL_MIMES.put(".tad", "application/octet-stream");
                ALL_MIMES.put(".talk", "text/x-speech");
                ALL_MIMES.put(".tar", "application/x-tar");
                ALL_MIMES.put(".taz", "application/x-tar");
                ALL_MIMES.put(".tbp", "application/x-timbuktu");
                ALL_MIMES.put(".tbt", "application/x-timbuktu");
                ALL_MIMES.put(".tcl", "application/x-tcl");
                ALL_MIMES.put(".tex", "application/x-tex");
                ALL_MIMES.put(".texi", "application/x-texinfo");
                ALL_MIMES.put(".texinfo", "application/x-texinfo");
                ALL_MIMES.put(".tgz", "application/x-compressed");
                ALL_MIMES.put(".thm", "application/vnd.eri.thm");
                ALL_MIMES.put(".tif", "image/tiff");
                ALL_MIMES.put(".tiff", "image/tiff");
                ALL_MIMES.put(".tki", "application/x-tkined");
                ALL_MIMES.put(".tkined", "application/x-tkined");
                ALL_MIMES.put(".toc", "application/toc");
                ALL_MIMES.put(".toy", "image/toy");
                ALL_MIMES.put(".tr", "application/x-troff");
                ALL_MIMES.put(".trk", "x-lml/x-gps");
                ALL_MIMES.put(".trm", "application/x-msterminal");
                ALL_MIMES.put(".tsi", "audio/tsplayer");
                ALL_MIMES.put(".tsp", "application/dsptype");
                ALL_MIMES.put(".tsv", "text/tab-separated-values");
                ALL_MIMES.put(".ttf", "application/octet-stream");
                ALL_MIMES.put(".ttz", "application/t-time");
                ALL_MIMES.put(".txt", "text/plain");
                ALL_MIMES.put(".uls", "text/iuls");
                ALL_MIMES.put(".ult", "audio/x-mod");
                ALL_MIMES.put(".ustar", "application/x-ustar");
                ALL_MIMES.put(".uu", "application/x-uuencode");
                ALL_MIMES.put(".uue", "application/x-uuencode");
                ALL_MIMES.put(".vcd", "application/x-cdlink");
                ALL_MIMES.put(".vcf", "text/x-vcard");
                ALL_MIMES.put(".vdo", "video/vdo");
                ALL_MIMES.put(".vib", "audio/vib");
                ALL_MIMES.put(".viv", "video/vivo");
                ALL_MIMES.put(".vivo", "video/vivo");
                ALL_MIMES.put(".vmd", "application/vocaltec-media-desc");
                ALL_MIMES.put(".vmf", "application/vocaltec-media-file");
                ALL_MIMES.put(".vmi", "application/x-dreamcast-vms-info");
                ALL_MIMES.put(".vms", "application/x-dreamcast-vms");
                ALL_MIMES.put(".vox", "audio/voxware");
                ALL_MIMES.put(".vqe", "audio/x-twinvq-plugin");
                ALL_MIMES.put(".vqf", "audio/x-twinvq");
                ALL_MIMES.put(".vql", "audio/x-twinvq");
                ALL_MIMES.put(".vre", "x-world/x-vream");
                ALL_MIMES.put(".vrml", "x-world/x-vrml");
                ALL_MIMES.put(".vrt", "x-world/x-vrt");
                ALL_MIMES.put(".vrw", "x-world/x-vream");
                ALL_MIMES.put(".vts", "workbook/formulaone");
                ALL_MIMES.put(".wav", "audio/x-wav");
                ALL_MIMES.put(".wax", "audio/x-ms-wax");
                ALL_MIMES.put(".wbmp", "image/vnd.wap.wbmp");
                ALL_MIMES.put(".wcm", "application/vnd.ms-works");
                ALL_MIMES.put(".wdb", "application/vnd.ms-works");
                ALL_MIMES.put(".web", "application/vnd.xara");
                ALL_MIMES.put(".wi", "image/wavelet");
                ALL_MIMES.put(".wis", "application/x-InstallShield");
                ALL_MIMES.put(".wks", "application/vnd.ms-works");
                ALL_MIMES.put(".wm", "video/x-ms-wm");
                ALL_MIMES.put(".wma", "audio/x-ms-wma");
                ALL_MIMES.put(".wmd", "application/x-ms-wmd");
                ALL_MIMES.put(".wmf", "application/x-msmetafile");
                ALL_MIMES.put(".wml", "text/vnd.wap.wml");
                ALL_MIMES.put(".wmlc", "application/vnd.wap.wmlc");
                ALL_MIMES.put(".wmls", "text/vnd.wap.wmlscript");
                ALL_MIMES.put(".wmlsc", "application/vnd.wap.wmlscriptc");
                ALL_MIMES.put(".wmlscript", "text/vnd.wap.wmlscript");
                ALL_MIMES.put(".wmv", "audio/x-ms-wmv");
                ALL_MIMES.put(".wmx", "video/x-ms-wmx");
                ALL_MIMES.put(".wmz", "application/x-ms-wmz");
                ALL_MIMES.put(".wpng", "image/x-up-wpng");
                ALL_MIMES.put(".wps", "application/vnd.ms-works");
                ALL_MIMES.put(".wpt", "x-lml/x-gps");
                ALL_MIMES.put(".wri", "application/x-mswrite");
                ALL_MIMES.put(".wrl", "x-world/x-vrml");
                ALL_MIMES.put(".wrz", "x-world/x-vrml");
                ALL_MIMES.put(".ws", "text/vnd.wap.wmlscript");
                ALL_MIMES.put(".wsc", "application/vnd.wap.wmlscriptc");
                ALL_MIMES.put(".wv", "video/wavelet");
                ALL_MIMES.put(".wvx", "video/x-ms-wvx");
                ALL_MIMES.put(".wxl", "application/x-wxl");
                ALL_MIMES.put(".x-gzip", "application/x-gzip");
                ALL_MIMES.put(".xaf", "x-world/x-vrml");
                ALL_MIMES.put(".xar", "application/vnd.xara");
                ALL_MIMES.put(".xbm", "image/x-xbitmap");
                ALL_MIMES.put(".xdm", "application/x-xdma");
                ALL_MIMES.put(".xdma", "application/x-xdma");
                ALL_MIMES.put(".xdw", "application/vnd.fujixerox.docuworks");
                ALL_MIMES.put(".xht", "application/xhtml+xml");
                ALL_MIMES.put(".xhtm", "application/xhtml+xml");
                ALL_MIMES.put(".xhtml", "application/xhtml+xml");
                ALL_MIMES.put(".xla", "application/vnd.ms-excel");
                ALL_MIMES.put(".xlc", "application/vnd.ms-excel");
                ALL_MIMES.put(".xll", "application/x-excel");
                ALL_MIMES.put(".xlm", "application/vnd.ms-excel");
                ALL_MIMES.put(".xls", "application/vnd.ms-excel");
                ALL_MIMES.put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                ALL_MIMES.put(".xlt", "application/vnd.ms-excel");
                ALL_MIMES.put(".xlw", "application/vnd.ms-excel");
                ALL_MIMES.put(".xm", "audio/x-mod");
                ALL_MIMES.put(".xml", "application/xml");
                ALL_MIMES.put(".xmz", "audio/x-mod");
                ALL_MIMES.put(".xof", "x-world/x-vrml");
                ALL_MIMES.put(".xpi", "application/x-xpinstall");
                ALL_MIMES.put(".xpm", "image/x-xpixmap");
                ALL_MIMES.put(".xsit", "text/xml");
                ALL_MIMES.put(".xsl", "text/xml");
                ALL_MIMES.put(".xul", "text/xul");
                ALL_MIMES.put(".xwd", "image/x-xwindowdump");
                ALL_MIMES.put(".xyz", "chemical/x-pdb");
                ALL_MIMES.put(".yz1", "application/x-yz1");
                ALL_MIMES.put(".z", "application/x-compress");
                ALL_MIMES.put(".zac", "application/x-zaurus-zac");
                ALL_MIMES.put(".zip", "application/zip");
                ALL_MIMES.put(".json", "application/json");
    }

    @SneakyThrows
    public static void downExcel(HttpServletResponse response, String fileName, Consumer<HttpServletResponse> consumer) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        consumer.accept(response);
    }

    public static void downloadFile(HttpServletResponse response, String fileName, InputStream inputStream) {
        // 获取MIME
        String finalFileName = fileName;
        ALL_MIMES.forEach((k, v) -> {
            if (finalFileName.endsWith(k)) {
                response.setContentType(v);
            }
        });
//        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            log.error("文件名修改失败!", e);
        }
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
        try {
            response.setHeader("Content-length", String.valueOf(inputStream.available()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        try {
            OutputStream os = response.getOutputStream();
            int i = 0;
            byte[] buffer = new byte[1024];
            while ((i = bis.read(buffer)) != -1) {
                os.write(buffer, 0, i);
            }
        } catch (IOException e) {
            log.error("文件流获取异常!", e);
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                log.error("文件流关闭异常!", e);
            }
        }
    }
}
