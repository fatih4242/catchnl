//
//  SplashScreen.swift
//  catchnl
//
//  Created by Fatih Toker on 14.09.2023.
//

import SwiftUI

struct SplashView: View {

    
    var body: some View {
        VStack(alignment: .center){
            Spacer()
            Image("logo-light")
            
            Spacer()
          
            
            Text("Powered by Toker Software")
                .font(.custom(Font.Poppins_SemiBold, size: 16))
                .foregroundColor(.white)
                
            
        }
        .frame(maxWidth: .infinity)
        .background(Color("primary-color"))
    }
}

struct SplashView_Previews: PreviewProvider {
    static var previews: some View {
        SplashView()
    }
}
