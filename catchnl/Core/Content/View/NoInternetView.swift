//
//  NoInternetView.swift
//  catchnl
//
//  Created by Fatih Toker on 15.09.2023.
//

import SwiftUI

struct NoInternetView: View {
    @EnvironmentObject var viewModel: ContentViewModel
    
    var body: some View {
        VStack{
            Image(systemName: "wifi.exclamationmark")
                .resizable()
                .scaledToFit()
                .frame(width: 250)
            
            Text("U Heeft geen Netwerk verbinding")
                .padding(.top)
                .font(.custom(Font.Poppins_SemiBold, size: 26))
                .multilineTextAlignment(.center)
            
            Button{
                viewModel.checkIfUserExists()
            } label: {
                Text("Opnieuw proberen")
                    .padding(.top)
                    .font(.custom(Font.Poppins_Regular, size: 20))
                    .multilineTextAlignment(.center)
                    .foregroundColor(.blue)
            }
        }
    }
}

struct NoInternetView_Previews: PreviewProvider {
    static var previews: some View {
        NoInternetView()
    }
}
