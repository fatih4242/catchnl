//
//  NotAcceptedView.swift
//  catchnl
//
//  Created by Fatih Toker on 14.09.2023.
//

import SwiftUI

struct NotAcceptedView: View {
    @EnvironmentObject var viewModel: ContentViewModel
    
    var body: some View {
        NavigationView{
            //TopBarView()
            VStack{
                Spacer()
                Text("Autoriteiten controleren uw gegevens")
                    .font(.custom(Font.Poppins_SemiBold, size: 26))
                    .multilineTextAlignment(.center)
                Image(systemName: "person.badge.clock")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 200)
                    .foregroundColor(Color("primary-color"))
                
                Spacer()
                
                Button{
                    viewModel.state = .loginView
                } label: {
                    Text("Wilt u een ander account gebruiken?")
                        .font(.custom(Font.Poppins_Regular, size: 16))
                        .foregroundColor(Color("secondry-color"))
                }
                                
            }
            .modifier(TopBarModifier(isFromMain: false))

        }
        
    }
}

struct NotAcceptedView_Previews: PreviewProvider {
    static var previews: some View {
        NotAcceptedView()
    }
}
