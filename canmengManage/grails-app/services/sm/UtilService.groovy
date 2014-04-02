package sm

class UtilService {

    boolean transactional = false

    //生成随机数值字符串
    String random(int length){
        if(length<1)
        return null

        Random randGen = new Random(new Date().getTime())
        char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray()
        char [] randBuffer = new char[length]
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer)
    }


}
